package ru.xpendence.nightwatchobserver.service;

import org.junit.Assert;
import org.junit.Test;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 05.03.19
 * Time: 18:43
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
public class AccessTokenServiceTest extends AbstractServiceTest {

    @Test
    public void save() {
    }

    @Test
    public void saveAccessToken() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void getAllAlive() {
    }

    @Test
    public void deleteExpired() {
    }

    @Test
    public void deleteAllByUserId() {
        Assert.assertNotNull(accessTokenService.getByUserId(user.getId()));
        accessTokenService.deleteAllByUserId(user.getUserId());

        Assert.assertNull(accessTokenService.getByUserId(user.getId()));
    }
}