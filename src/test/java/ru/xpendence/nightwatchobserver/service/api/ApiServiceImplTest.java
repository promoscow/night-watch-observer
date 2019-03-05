package ru.xpendence.nightwatchobserver.service.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.xpendence.nightwatchobserver.entity.User;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 05.03.19
 * Time: 15:04
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("remote-test")
public class ApiServiceImplTest {
    private static final Integer USER_ID = 297834325;
    private static final String ACCESS_TOKEN = "98743c6587d365nc8734d65";
    private static final String EMAIL = "testing987432@gmail.com";
    private static final Integer EXPIRES_IN = 43200;

    @Test
    public void authorize_updateDataOfExistingUser() {
        User newUser = createUser(USER_ID, EMAIL, ACCESS_TOKEN, EXPIRES_IN);
//        User existingUser =
    }

    private User createUser(Integer userId, String email, String accessToken, Integer expiresIn) {
        return User.of(userId, email, accessToken, expiresIn);
    }

    @Test
    public void scan() {
    }

    @Test
    public void send() {
    }
}