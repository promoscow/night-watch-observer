package ru.xpendence.nightwatchobserver.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.xpendence.nightwatchobserver.entity.AccessToken;
import ru.xpendence.nightwatchobserver.entity.AuthCode;
import ru.xpendence.nightwatchobserver.entity.User;
import ru.xpendence.nightwatchobserver.service.api.ApiService;

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
    protected static final Integer USER_ID;
    protected static final String ACCESS_TOKEN;
    protected static final String EMAIL;
    protected static final Integer EXPIRES_IN;
    protected static final String AUTH_CODE;

    static {
        USER_ID = new Random().nextInt(Integer.MAX_VALUE - 1000000) + 1000000;
        ACCESS_TOKEN = UUID.randomUUID().toString();
        EMAIL = String.format("testing%s@gmail.com", new Random().nextInt(10000) + 10000);
        EXPIRES_IN = 43200;
        AUTH_CODE = RandomStringUtils.randomAlphanumeric(20);
    }

    @Autowired
    public UserService userService;

    @Autowired
    public AccessTokenService accessTokenService;

    @Autowired
    public ApiService apiService;

    @Autowired
    public AuthCodeService authCodeService;

    AuthCode code;
    AccessToken token;
    User user;

    @Before
    public void init() {
        code = createAuthCode();
        token = createAccessToken(ACCESS_TOKEN, EXPIRES_IN);
        user = userService.saveUser(User.of(USER_ID, EMAIL, token));
    }

    @Transactional
    public User createUser(Integer userId, String email, String accessToken, Integer expiresIn) {
//        AccessToken token = createAccessToken(accessToken, expiresIn);
        User user = userService.saveUser(User.of(userId, email, token));
        authCodeService.saveAuthCode(new AuthCode(AUTH_CODE, user));

        token.setUser(user);
        accessTokenService.saveAccessToken(token);
        return user;
    }

    private AccessToken createAccessToken(String accessToken, Integer expiresIn) {
        return new AccessToken(accessToken, expiresIn);
    }

    private AuthCode createAuthCode() {
        return authCodeService.saveAuthCode(new AuthCode(AUTH_CODE));
    }

    public String generateAccessToken() {
        return UUID.randomUUID().toString();
    }
}
