package ru.xpendence.nightwatchobserver.service.api;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.xpendence.nightwatchobserver.entity.AccessToken;
import ru.xpendence.nightwatchobserver.entity.User;
import ru.xpendence.nightwatchobserver.service.AccessTokenService;
import ru.xpendence.nightwatchobserver.service.UserService;

import java.util.Random;
import java.util.UUID;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 05.03.19
 * Time: 15:58
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("remote-test")
public abstract class AbstractServiceTest {
    static final Integer USER_ID;
    static final String ACCESS_TOKEN;
    static final String EMAIL;
    static final Integer EXPIRES_IN;

    static {
        USER_ID = new Random().nextInt(Integer.MAX_VALUE - 1000000) + 1000000;
        ACCESS_TOKEN = UUID.randomUUID().toString();
        EMAIL = String.format("testing%s@gmail.com", new Random().nextInt(10000) + 10000);
        EXPIRES_IN = 43200;
    }

    @Autowired
    UserService userService;

    @Autowired
    AccessTokenService accessTokenService;

    @Autowired
    ApiService apiService;

    @Transactional
    User createUser(Integer userId, String email, String accessToken, Integer expiresIn) {
        AccessToken token = createAccessToken(accessToken, expiresIn);
        User user = userService.saveUser(User.of(userId, email, token));
        token.setUser(user);
        accessTokenService.saveAccessToken(token);
        return user;
    }

    private AccessToken createAccessToken(String accessToken, Integer expiresIn) {
        return new AccessToken(accessToken, expiresIn);
    }

    String generateAccessToken() {
        return UUID.randomUUID().toString();
    }
}
