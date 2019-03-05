package ru.xpendence.nightwatchobserver.service.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.wall.WallPostFull;
import com.vk.api.sdk.objects.wall.WallpostAttachment;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import com.vk.api.sdk.queries.wall.WallGetFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.xpendence.nightwatchobserver.dto.ToRecognitionDto;
import ru.xpendence.nightwatchobserver.entity.User;
import ru.xpendence.nightwatchobserver.entity.WallPost;
import ru.xpendence.nightwatchobserver.entity.WallPostPhoto;
import ru.xpendence.nightwatchobserver.exception.AuthException;
import ru.xpendence.nightwatchobserver.repository.UserRepository;
import ru.xpendence.nightwatchobserver.repository.WallPostPhotoRepository;
import ru.xpendence.nightwatchobserver.repository.WallPostRepository;
import ru.xpendence.nightwatchobserver.service.UserService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 26.02.19
 * Time: 22:56
 * e-mail: 2262288@gmail.com
 */
@Service
@Profile({"!dev", "!local-mock", "!remote-mock"})
@Slf4j
public class ApiServiceImpl extends AbstractApiService {

    private final VkApiClient vk;
    private final WallPostRepository wallPostRepository;
    private final WallPostPhotoRepository wallPostPhotoRepository;
    private final ObjectMapper objectMapper;

    @Value("${app.id}")
    private Integer appId;

    @Value("${client.secret}")
    private String clientSecret;

    @Value("${redirect.auth.uri}")
    private String redirectUri;

    @Value("${site.recognition.url}")
    private String siteRecognitionUrl;

    @Autowired
    public ApiServiceImpl(UserRepository userRepository,
                          VkApiClient vk,
                          WallPostRepository wallPostRepository,
                          UserService userService,
                          WallPostPhotoRepository wallPostPhotoRepository,
                          ObjectMapper objectMapper) {
        super(userRepository, userService);
        this.vk = vk;
        this.wallPostRepository = wallPostRepository;
        this.wallPostPhotoRepository = wallPostPhotoRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public User authorize(String code) {
        UserAuthResponse authResponse = obtainUserAuthResponse(code);
        User user = User.of(
                authResponse.getUserId(),
                authResponse.getEmail(),
                authResponse.getAccessToken(),
                authResponse.getExpiresIn()
        );
        checkIfUserExists(authResponse.getUserId(), user);
        return user;
    }

    @Override
    public Boolean scan(Long userId) {
        UserActor userActor = getActorByUserId(userId);
        log.info("Created userActor with parameters: {}", userActor.toString());

        GetResponse response = obtainPosts(userActor);
        List<WallPostFull> wallPosts = response.getItems();
        log.info("Obtained posts: {}", wallPosts.size());
        List<WallPost> existingPosts = wallPostRepository.findAllByUserId(userId);
        User user = userService.getById(userId);
        List<WallPost> posts = transformPosts(wallPosts, existingPosts, user);
        log.info("< --- preparing to send {} posts", posts.size());
        sendToRecognition(posts);
        return !posts.isEmpty();
    }

    @Async
    @Override
    public void send(ToRecognitionDto dto) {
        log.info("--- > Sending dto: {}", dto.toString());
        ResponseEntity<String> response = new RestTemplate().postForEntity(siteRecognitionUrl, dto, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Sent successfully: {}\nResponse status: {}\nMessage: {}",
                    toJson(dto), response.getStatusCodeValue(), response.getBody());
        } else {
            log.info("Sending failed: {}\nResponse status: {}\nMessage: {}",
                    toJson(dto), response.getStatusCodeValue(), response.getBody());
        }
    }

    private void sendToRecognition(List<WallPost> posts) {
        if (!posts.isEmpty()) {
            posts
                    .stream()
                    .filter(p -> Objects.nonNull(p.getPhotos()) && !p.getPhotos().isEmpty())
                    .flatMap(p -> p.getPhotos().stream())
                    .map(this::transformToToRecognitionDto)
                    .forEach(this::send);
        }
    }

    private ToRecognitionDto transformToToRecognitionDto(WallPostPhoto wallPostPhoto) {
        return ToRecognitionDto.of(
                wallPostPhoto.getId(),
                wallPostPhoto.getPhoto604Url(),
                wallPostPhoto.getPost().getId(),
                wallPostPhoto.getPost().getOwnerId(),
                wallPostPhoto.getPost().getText()
        );
    }


    // TODO: 02.03.19 переписать через SQL-запросы
    private List<WallPost> transformPosts(List<WallPostFull> wallPosts, List<WallPost> existingPosts, User user) {
        log.info("wallPosts count: {}", wallPosts.size());
        log.info("existing posts: {}", existingPosts.size());
        List<Integer> postIds = existingPosts.stream().map(WallPost::getPostId).collect(Collectors.toList());
        log.info("postIds: {}", postIds);
        List<WallPost> posts = wallPosts
                .stream()
//                .filter(w -> !checkIfExists(w.getId(), postIds))
                .map(w -> transform(w, user))
                .collect(Collectors.toList());
        return wallPostRepository.saveAll(posts);
    }

    private WallPost transform(WallPostFull wallPostFull, User user) {
        WallPost wallPost = WallPost.ofWallPostFull(
                wallPostFull.getId(),
                wallPostFull.getFromId(),
                wallPostFull.getOwnerId(),
                wallPostFull.getDate(),
                user
        );
        if (Objects.isNull(wallPostFull.getCopyHistory()) || wallPostFull.getCopyHistory().isEmpty()) {
            return wallPost;
        }
        com.vk.api.sdk.objects.wall.WallPost originalPost = wallPostFull.getCopyHistory().get(0);
        wallPost.setText(originalPost.getText().replaceAll("[^а-яА-Яa-zA-Z0-9 .,]", ""));
        wallPost.setOriginalPostId(originalPost.getId());
        wallPost.setOriginalOwnerPostId(originalPost.getOwnerId());
        wallPost.setPhotos(getWallPostPhotos(originalPost.getAttachments(), wallPost));
        return wallPost;
    }

    private List<WallPostPhoto> getWallPostPhotos(List<WallpostAttachment> attachments, WallPost wallPost) {
        return Objects.isNull(attachments) ? null : wallPostPhotoRepository.saveAll(attachments
                .stream()
                .filter(a -> Objects.nonNull(a.getPhoto()))
                .map(a -> getPhotoAsWallPostPhoto(a.getPhoto(), wallPost))
                .collect(Collectors.toList()));
    }

    private WallPostPhoto getPhotoAsWallPostPhoto(Photo photo, WallPost wallPost) {
        return WallPostPhoto.of(photo.getId(), photo.getPhoto604(), wallPost);
    }

    private boolean checkIfExists(Integer id, List<Integer> postIds) {
        return postIds.stream().anyMatch(p -> p.equals(id));
    }

    private GetResponse obtainPosts(UserActor userActor) {
        GetResponse response1 = null;
        try {
            response1 = vk
                    .wall()
                    .get(userActor)
                    .ownerId(userActor.getId())
                    .count(1)
                    .filter(WallGetFilter.ALL)
                    .execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        return response1;
    }

    private UserAuthResponse obtainUserAuthResponse(String code) {
        UserAuthResponse authResponse;
        try {
            authResponse = vk.oauth()
                    .userAuthorizationCodeFlow(appId, clientSecret, redirectUri, code)
                    .execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
            throw new AuthException(String.format(
                    "Unable to auth with\n\tAPP_ID: %d\n\tCLIENT_SECRET: %s\n\tREDIRECT_URI: %s",
                    appId,
                    clientSecret,
                    redirectUri
            ));
        }
        return authResponse;
    }

    private String toJson(ToRecognitionDto dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
