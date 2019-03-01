package ru.xpendence.nightwatchobserver.service.api;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.UserAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.xpendence.nightwatchobserver.entity.User;
import ru.xpendence.nightwatchobserver.exception.AuthException;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 26.02.19
 * Time: 22:56
 * e-mail: 2262288@gmail.com
 */
@Service
@Profile("local")
public class ApiServiceImpl implements ApiService {

    private final VkApiClient vk;

    @Value("#{new Integer.parseInt('${app.id}')}")
    private Integer appId;

    @Value("${client.secret}")
    private String clientSecret;

    @Value("${redirect.auth.uri}")
    private String redirectUri;

    @Autowired
    public ApiServiceImpl(VkApiClient vk) {
        this.vk = vk;
    }

    @Override
    public User authorize(String code) {
        UserAuthResponse authResponse = obtainUserAuthResponse(code);
        UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
        return User.ofActor(actor);
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
