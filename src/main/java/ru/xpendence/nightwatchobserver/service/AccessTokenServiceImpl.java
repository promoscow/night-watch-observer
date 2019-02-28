package ru.xpendence.nightwatchobserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.xpendence.nightwatchobserver.dto.AccessTokenDto;
import ru.xpendence.nightwatchobserver.mapper.AccessTokenMapper;
import ru.xpendence.nightwatchobserver.repository.AccessTokenRepository;

import java.util.List;
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

    @Autowired
    public AccessTokenServiceImpl(AccessTokenRepository repository,
                                  AccessTokenMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(AccessTokenDto dto) {
        repository.save(mapper.toEntity(dto));
    }

    @Override
    public List<AccessTokenDto> getAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
