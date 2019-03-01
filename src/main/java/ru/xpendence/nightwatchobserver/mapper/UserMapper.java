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

//    @PostConstruct
//    public void setupMapper() {
//        mapper.createTypeMap(User.class, UserDto.class)
//                .addMappings(m -> {
//                    m.skip(UserDto::setAuthCode);
//                    m.skip(UserDto::setAccessToken);
//                }).setPostConverter(toDtoConverter());
//        mapper.createTypeMap(UserDto.class, User.class)
//                .addMappings(m -> {
//                    m.skip(User::setAuthCode);
//                    m.skip(User::setAccessToken);
//                });
//    }
//
//    @Override
//    public void mapSpecificFields(User source, UserDto destination) {
//        if (Objects.nonNull(source) && Objects.nonNull(source.))
//    }
//
//    @Override
//    public void mapSpecificFields(UserDto source, User destination) {
//        super.mapSpecificFields(source, destination);
//    }
}
