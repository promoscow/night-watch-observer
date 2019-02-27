package ru.xpendence.nightwatchobserver.mapper;

import org.springframework.stereotype.Component;
import ru.xpendence.nightwatchobserver.dto.AuthCodeDto;
import ru.xpendence.nightwatchobserver.entity.AuthCode;
import ru.xpendence.nightwatchobserver.mapper.engine.AbstractMapper;
import ru.xpendence.nightwatchobserver.mapper.engine.Mapper;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 27.02.19
 * Time: 21:14
 * e-mail: 2262288@gmail.com
 */
@Component
@Mapper(entity = AuthCode.class, dto = AuthCodeDto.class)
public class AuthCodeMapper extends AbstractMapper<AuthCode, AuthCodeDto> {
}
