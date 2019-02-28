package ru.xpendence.nightwatchobserver.mapper;

import org.springframework.stereotype.Component;
import ru.xpendence.nightwatchobserver.dto.UserDto;
import ru.xpendence.nightwatchobserver.entity.User;
import ru.xpendence.nightwatchobserver.mapper.engine.AbstractMapper;
import ru.xpendence.nightwatchobserver.mapper.engine.Mapper;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 28.02.19
 * Time: 15:24
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@Component
@Mapper(entity = User.class, dto = UserDto.class)
public class UserMapper extends AbstractMapper<User, UserDto> {
}
