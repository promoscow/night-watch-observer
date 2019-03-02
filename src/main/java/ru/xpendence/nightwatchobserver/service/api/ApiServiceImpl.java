package ru.xpendence.nightwatchobserver.service.api;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.wall.WallPostFull;
import com.vk.api.sdk.objects.wall.responses.GetRepostsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.xpendence.nightwatchobserver.entity.User;
import ru.xpendence.nightwatchobserver.exception.AuthException;
import ru.xpendence.nightwatchobserver.repository.UserRepository;

import java.util.List;

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

    @Value("${app.id}")
    private Integer appId;

    @Value("${client.secret}")
    private String clientSecret;

    @Value("${redirect.auth.uri}")
    private String redirectUri;

    @Autowired
    public ApiServiceImpl(UserRepository userRepository,
                          VkApiClient vk) {
        super(userRepository);
        this.vk = vk;
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
        log.info("Create userActor with parameters: {}", userActor.toString());

        GetRepostsResponse response = obtainPosts(userActor);
        List<WallPostFull> wallPosts = response.getItems();

        return true;
    }

    private GetRepostsResponse obtainPosts(UserActor userActor) {
        GetRepostsResponse response = null;
        try {
            response = vk
                    .wall()
                    .getReposts(userActor)
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
