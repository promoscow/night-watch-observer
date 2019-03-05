package ru.xpendence.nightwatchobserver.service;

import org.junit.Assert;
import org.junit.Test;
import ru.xpendence.nightwatchobserver.entity.User;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 05.03.19
 * Time: 18:24
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
public class AuthCodeServiceTest extends AbstractServiceTest {

    @Test
    public void save() {
    }

    @Test
    public void saveAuthCode() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void getAll1() {
    }

    @Test
    public void getLast() {
    }

    @Test
    public void get() {
    }

    @Test
    public void deleteUsed() {
    }

    @Test
    public void deleteAllAuthCodesForUser() {
        User user = createUser(USER_ID, EMAIL, ACCESS_TOKEN, EXPIRES_IN);
        authCodeService.deleteAllAuthCodesForUser(user.getUserId());

        Assert.assertNull(authCodeService.getByUserId(user.getId()));
    }
}