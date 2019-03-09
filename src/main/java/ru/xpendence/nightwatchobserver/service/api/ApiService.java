package ru.xpendence.nightwatchobserver.service.api;

import com.vk.api.sdk.client.actors.UserActor;
import org.springframework.transaction.annotation.Transactional;
import ru.xpendence.nightwatchobserver.dto.AccessTokenDto;
import ru.xpendence.nightwatchobserver.dto.ToRecognitionDto;
import ru.xpendence.nightwatchobserver.entity.AccessToken;
import ru.xpendence.nightwatchobserver.entity.User;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 26.02.19
 * Time: 22:55
 * e-mail: 2262288@gmail.com
 */
public interface ApiService {

    AccessTokenDto authByCode(String code);

    User authorize(String code);

    @Transactional
    Boolean scan(Long userId);

    @Transactional
    void scanAll();

    boolean validateAccessToken(AccessToken accessToken);

    UserActor getActorByUserId(Long userId);

    void send(ToRecognitionDto dto);

    void checkIfUserExists(Integer authResponse, User user);
}
