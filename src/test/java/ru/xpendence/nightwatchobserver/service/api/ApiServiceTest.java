package ru.xpendence.nightwatchobserver.service.api;

import org.junit.Assert;
import org.junit.Test;
import ru.xpendence.nightwatchobserver.entity.User;
import ru.xpendence.nightwatchobserver.service.AbstractServiceTest;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 05.03.19
 * Time: 15:04
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
public class ApiServiceTest extends AbstractServiceTest {

    @Test
    public void authorize_updateDataOfExistingUser() {
        String accessToken = generateAccessToken();
        User existingUser = User.of(USER_ID, EMAIL, accessToken, EXPIRES_IN);
        apiService.checkIfUserExists(USER_ID, existingUser);
        userService.saveUser(existingUser);

        Assert.assertEquals(existingUser.getId(), user.getId());
    }

    @Test
    public void scan() {
    }

    @Test
    public void send() {
    }
}