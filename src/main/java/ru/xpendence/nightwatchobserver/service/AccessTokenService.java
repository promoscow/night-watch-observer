package ru.xpendence.nightwatchobserver.service;

import ru.xpendence.nightwatchobserver.dto.AccessTokenDto;
import ru.xpendence.nightwatchobserver.entity.AccessToken;

import java.util.List;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 28.02.19
 * Time: 15:23
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
public interface AccessTokenService {

    AccessTokenDto save(AccessTokenDto dto);

    AccessToken saveAccessToken(AccessToken token);

    AccessToken getByUserId(Long userId);

    List<AccessTokenDto> getAll();

    List<AccessToken> getAllAlive();

    void deleteExpired();

    void deleteAllByUserId(Integer userId);
}
