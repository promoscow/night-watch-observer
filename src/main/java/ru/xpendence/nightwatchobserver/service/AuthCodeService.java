package ru.xpendence.nightwatchobserver.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.xpendence.nightwatchobserver.dto.AuthCodeDto;
import ru.xpendence.nightwatchobserver.entity.AuthCode;

import java.util.List;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 27.02.19
 * Time: 21:06
 * e-mail: 2262288@gmail.com
 */
public interface AuthCodeService {

    AuthCodeDto save(AuthCodeDto dto);

    AuthCode saveAuthCode(AuthCode code);

    AuthCode getByUserId(Long userId);

    List<AuthCodeDto> getAll();

    Page<AuthCodeDto> getAll(Pageable pageable);

    AuthCodeDto getLast();

    AuthCodeDto get(Long id);

    void deleteUsed();

    void deleteAllAuthCodesForUser(Integer userId);
}
