package ru.xpendence.nightwatchobserver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xpendence.nightwatchobserver.dto.AuthCodeDto;
import ru.xpendence.nightwatchobserver.entity.AccessToken;
import ru.xpendence.nightwatchobserver.entity.AuthCode;
import ru.xpendence.nightwatchobserver.entity.User;
import ru.xpendence.nightwatchobserver.mapper.AuthCodeMapper;
import ru.xpendence.nightwatchobserver.repository.AuthCodeRepository;
import ru.xpendence.nightwatchobserver.service.api.ApiService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 27.02.19
 * Time: 21:07
 * e-mail: 2262288@gmail.com
 */
@Service
@Slf4j
public class AuthCodeServiceImpl implements AuthCodeService {

    private final AuthCodeRepository repository;
    private final AuthCodeMapper mapper;
    private final ApiService apiService;
    private final UserService userService;
    private final AccessTokenService accessTokenService;

    @Autowired
    public AuthCodeServiceImpl(AuthCodeRepository repository,
                               AuthCodeMapper mapper,
                               ApiService apiService,
                               UserService userService,
                               AccessTokenService accessTokenService) {
        this.repository = repository;
        this.mapper = mapper;
        this.apiService = apiService;
        this.userService = userService;
        this.accessTokenService = accessTokenService;
    }

    @Override
    @Transactional
    public AuthCodeDto save(AuthCodeDto dto) {

        AuthCode authCode = mapper.toEntity(dto);
        User user = apiService.authorize(authCode.getCode());

        deleteAllAuthCodesForUser(user.getUserId());
        accessTokenService.deleteAllByUserId(user.getUserId());

        user = userService.saveUser(user);
        authCode.setUser(user);
        authCode = saveAuthCode(authCode);

        AccessToken accessToken = user.getAccessToken();
        accessToken.setUser(user);
        AccessToken token = accessTokenService.saveAccessToken(accessToken);

        user.setAccessToken(token);
        user.setAuthCode(authCode);
        userService.saveUser(user);

        return mapper.toDto(authCode);
    }

    @Override
    public AuthCode saveAuthCode(AuthCode code) {
        return repository.saveAndFlush(code);
    }

    @Override
    public AuthCode getByUserId(Long userId) {
        return repository.getAllByUserId(userId);
    }

    @Override
    public List<AuthCodeDto> getAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Page<AuthCodeDto> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public AuthCodeDto getLast() {
        return null;
    }

    @Override
    public AuthCodeDto get(Long id) {
        return null;
    }

    @Override
    @Scheduled(cron = "20 * * * * ?")
    public void deleteUsed() {
        List<Long> usedCodeIds = repository.findAllIdUsed();
        repository.deleteAllByIdIn(usedCodeIds);
        log.info("Auth codes deleted: {}", usedCodeIds.size());
    }

    @Override
    public void deleteAllAuthCodesForUser(Integer userId) {
        repository.deleteAllByUser(userId);
    }
}
