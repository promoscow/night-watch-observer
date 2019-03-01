package ru.xpendence.nightwatchobserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xpendence.nightwatchobserver.dto.AuthCodeDto;
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
        User userFromApi = apiService.authorize(authCode.getCode());




//        AuthCode authCode = mapper.toEntity(dto);
//        User userFromAuthorized = apiService.authorize(dto.getCode());
//        AccessToken savedAccessToken = accessTokenService.saveAccessToken(userFromAuthorized.getAccessTokens().get(0));
//        userFromAuthorized.addAccessToken(savedAccessToken);
//        User user = userService.saveUser(userFromAuthorized);
//        authCode.setUser(user);
//        authCode = repository.save(authCode);
//        return mapper.toDto(authCode);
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
}
