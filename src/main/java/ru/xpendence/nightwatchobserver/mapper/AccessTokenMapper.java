package ru.xpendence.nightwatchobserver.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xpendence.nightwatchobserver.dto.AccessTokenDto;
import ru.xpendence.nightwatchobserver.entity.AccessToken;
import ru.xpendence.nightwatchobserver.mapper.engine.AbstractMapper;
import ru.xpendence.nightwatchobserver.mapper.engine.Mapper;
import ru.xpendence.nightwatchobserver.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 28.02.19
 * Time: 15:28
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@Component
@Mapper(entity = AccessToken.class, dto = AccessTokenDto.class)
public class AccessTokenMapper extends AbstractMapper<AccessToken, AccessTokenDto> {

    private final UserRepository userRepository;

    @Autowired
    public AccessTokenMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(AccessToken.class, AccessTokenDto.class)
                .addMappings(m -> m.skip(AccessTokenDto::setUserId)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(AccessTokenDto.class, AccessToken.class)
                .addMappings(m -> m.skip(AccessToken::setUser)).setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFields(AccessToken source, AccessTokenDto destination) {
        if (Objects.nonNull(destination) && Objects.nonNull(destination.getUserId())) {
            destination.setUserId(source.getUser().getId());
        }
    }

    @Override
    public void mapSpecificFields(AccessTokenDto source, AccessToken destination) {
        destination.setUser(userRepository.findById(source.getUserId()).orElse(null));
    }
}
