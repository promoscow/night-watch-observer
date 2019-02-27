package ru.xpendence.nightwatchobserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.xpendence.nightwatchobserver.dto.AuthCodeDto;
import ru.xpendence.nightwatchobserver.mapper.AuthCodeMapper;
import ru.xpendence.nightwatchobserver.repository.AuthCodeRepository;

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

    @Autowired
    public AuthCodeServiceImpl(AuthCodeRepository repository,
                               AuthCodeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public AuthCodeDto save(AuthCodeDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
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
