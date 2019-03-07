package ru.xpendence.nightwatchobserver.service;

import org.junit.Assert;
import org.junit.Test;

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

        Assert.assertNotNull(authCodeService.getByUserId(user.getId()));
        authCodeService.deleteAllAuthCodesForUser(user.getUserId());
        Assert.assertNull(authCodeService.getByUserId(user.getId()));
    }
}