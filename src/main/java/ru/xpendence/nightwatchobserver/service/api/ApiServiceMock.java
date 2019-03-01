package ru.xpendence.nightwatchobserver.service.api;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.xpendence.nightwatchobserver.entity.User;

import java.util.Random;
import java.util.UUID;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 01.03.19
 * Time: 13:41
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@Service
@Profile(value = {"dev", "local-mock"})
public class ApiServiceMock implements ApiService {

    @Override
    public User authorize(String code) {
//        UserActor userActor = new UserActor(new Random().nextInt(100000) + 1, UUID.randomUUID().toString());
        return User.of(new Random().nextInt(100000) + 1, "test@mail.ru", UUID.randomUUID().toString(), 43800);
    }
}
