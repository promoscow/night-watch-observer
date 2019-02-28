package ru.xpendence.nightwatchobserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.xpendence.nightwatchobserver.dto.UserDto;
import ru.xpendence.nightwatchobserver.entity.User;
import ru.xpendence.nightwatchobserver.exception.UserException;
import ru.xpendence.nightwatchobserver.mapper.UserMapper;
import ru.xpendence.nightwatchobserver.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 28.02.19
 * Time: 15:24
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository repository,
                           UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public UserDto save(UserDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public User saveUser(User user) {
        return repository.save(user);
    }

    @Override
    public User getUser(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public UserDto get(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(
                () -> new UserException(String.format("User not found by id: %d", id))
        ));
    }

    @Override
    public List<UserDto> getAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
