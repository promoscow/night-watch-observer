package ru.xpendence.nightwatchobserver.service.api;

import com.vk.api.sdk.client.actors.UserActor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.xpendence.nightwatchobserver.dto.AccessTokenDto;
import ru.xpendence.nightwatchobserver.dto.ToRecognitionDto;
import ru.xpendence.nightwatchobserver.entity.User;
import ru.xpendence.nightwatchobserver.repository.UserRepository;
import ru.xpendence.nightwatchobserver.service.UserService;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 01.03.19
 * Time: 13:41
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@Service
@Profile(value = {"dev", "local-mock", "remote-mock", "remote-test"})
@Slf4j
public class ApiServiceMock extends AbstractApiService {

    public ApiServiceMock(UserRepository userRepository, UserService userService) {
        super(userRepository, userService);
    }

    @Override
    public Boolean scan(Long userId) {
        UserActor userActor = getActorByUserId(userId);
        log.info("Create userActor with parameters: {}", userActor.toString());
        return Objects.nonNull(userActor);
    }

    @Override
    public void send(ToRecognitionDto dto) {
    }

    // TODO: 09.03.19 написать реализацию заглушки
    @Override
    public AccessTokenDto authByCode(String code) {
        return null;
    }

    @Override
    public User authorize(String code) {
//        UserActor userActor = new UserActor(new Random().nextInt(100000) + 1, UUID.randomUUID().toString());
        return User.of(new Random().nextInt(100000) + 1, "test@mail.ru", UUID.randomUUID().toString(), 43800);
    }
}
