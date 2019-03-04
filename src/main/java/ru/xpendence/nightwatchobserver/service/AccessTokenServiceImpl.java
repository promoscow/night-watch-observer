package ru.xpendence.nightwatchobserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.xpendence.nightwatchobserver.dto.AccessTokenDto;
import ru.xpendence.nightwatchobserver.entity.AccessToken;
import ru.xpendence.nightwatchobserver.entity.User;
import ru.xpendence.nightwatchobserver.mapper.AccessTokenMapper;
import ru.xpendence.nightwatchobserver.repository.AccessTokenRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 28.02.19
 * Time: 15:23
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    private final AccessTokenRepository repository;
    private final AccessTokenMapper mapper;
    private final UserService userService;

    @Autowired
    public AccessTokenServiceImpl(AccessTokenRepository repository,
                                  AccessTokenMapper mapper,
                                  UserService userService) {
        this.repository = repository;
        this.mapper = mapper;
        this.userService = userService;
    }

    @Override
    public AccessTokenDto save(AccessTokenDto dto) {
        AccessToken accessToken = mapper.toEntity(dto);
        validateUser(accessToken, dto);
        return mapper.toDto(repository.saveAndFlush(accessToken));
    }

    @Override
    public AccessToken saveAccessToken(AccessToken token) {
        return repository.saveAndFlush(token);
    }

    @Override
    public List<AccessTokenDto> getAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<AccessToken> getAllAlive() {
        return repository.findAllByExternalOrExpiresInAfter(true, LocalDateTime.now());
    }

    @Override
//    @Scheduled(cron = "30 * * * * ?")
    public void deleteExpired() {
        repository.deleteAllByExternalFalseAndExpiresInBefore(LocalDateTime.now());
    }

    private void validateUser(AccessToken accessToken, AccessTokenDto dto) {
        if (Objects.nonNull(dto.getUserId()) && Objects.isNull(userService.getUser(dto.getUserId()))) {
            accessToken.setUser(userService.saveUser(User.ofAccessToken(accessToken)));
        }
    }
}
