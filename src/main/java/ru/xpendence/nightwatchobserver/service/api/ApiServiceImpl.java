package ru.xpendence.nightwatchobserver.service.api;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.wall.WallPostFull;
import com.vk.api.sdk.objects.wall.WallpostAttachment;
import com.vk.api.sdk.objects.wall.responses.GetRepostsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
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
@Profile("local")
@Slf4j
public class ApiServiceImpl extends AbstractApiService {

    private final VkApiClient vk;
    private final WallPostRepository wallPostRepository;
    private final UserService userService;
    private final WallPostPhotoRepository wallPostPhotoRepository;

    @Value("${app.id}")
    private Integer appId;

    @Value("${client.secret}")
    private String clientSecret;

    @Value("${redirect.auth.uri}")
    private String redirectUri;

    @Autowired
    public ApiServiceImpl(UserRepository userRepository,
                          VkApiClient vk,
                          WallPostRepository wallPostRepository,
                          UserService userService,
                          WallPostPhotoRepository wallPostPhotoRepository) {
        super(userRepository);
        this.vk = vk;
        this.wallPostRepository = wallPostRepository;
        this.userService = userService;
        this.wallPostPhotoRepository = wallPostPhotoRepository;
    }

    @Override
    public User authorize(String code) {
        UserAuthResponse authResponse = obtainUserAuthResponse(code);
//        UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
        return User.of(
                authResponse.getUserId(),
                authResponse.getEmail(),
                authResponse.getAccessToken(),
                authResponse.getExpiresIn()
        );
    }

    @Override
    public Boolean scan(Long userId) {
        UserActor userActor = getActorByUserId(userId);
        log.info("Created userActor with parameters: {}", userActor.toString());

        GetRepostsResponse response = obtainPosts(userActor);
        List<WallPostFull> wallPosts = response.getItems();
        log.info("Obtained posts: {}", wallPosts);
        List<WallPost> existingPosts = wallPostRepository.findAllByUserId(userId);
        User user = userService.getById(userId);
        List<WallPost> posts = transformPosts(wallPosts, existingPosts, user);
        return !posts.isEmpty();
    }

    // TODO: 02.03.19 переписать через SQL-запросы
    private List<WallPost> transformPosts(List<WallPostFull> wallPosts, List<WallPost> existingPosts, User user) {
        List<Integer> postIds = existingPosts.stream().map(WallPost::getPostId).collect(Collectors.toList());
        return wallPostRepository.saveAll(wallPosts
                .stream()
                .filter(w -> !checkIfExists(w.getId(), postIds))
                .map(w -> transform(w, user))
                .collect(Collectors.toList()));
    }

    private WallPost transform(WallPostFull wallPostFull, User user) {
        WallPost wallPost = WallPost.ofWallPostFull(
                wallPostFull.getId(),
                wallPostFull.getFromId(),
                wallPostFull.getOwnerId(),
                wallPostFull.getDate(),
                wallPostFull.getText(),
                user
        );
        wallPost.setPhotos(getWallPostPhotos(wallPostFull.getAttachments(), wallPost));
        return wallPost;
    }

    private List<WallPostPhoto> getWallPostPhotos(List<WallpostAttachment> attachments, WallPost wallPost) {
        return wallPostPhotoRepository.saveAll(attachments
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

    private GetRepostsResponse obtainPosts(UserActor userActor) {
        GetRepostsResponse response = null;
        try {
            response = vk
                    .wall()
                    .getReposts(userActor)
                    .count(10)
                    .execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        return response;
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
}
