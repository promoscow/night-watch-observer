package ru.xpendence.nightwatchobserver.service;

import ru.xpendence.nightwatchobserver.dto.AccessTokenDto;

import java.util.List;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 28.02.19
 * Time: 15:23
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
public interface AccessTokenService {

    AccessTokenDto save(AccessTokenDto dto);

    List<AccessTokenDto> getAll();
}
