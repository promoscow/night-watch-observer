package ru.xpendence.nightwatchobserver.service;

import ru.xpendence.nightwatchobserver.dto.UserDto;
import ru.xpendence.nightwatchobserver.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 28.02.19
 * Time: 15:23
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
public interface UserService {

    UserDto save(UserDto dto);

    User saveUser(User user);

    User getUser(Long id);

    Optional<User> getUserByUserId(Integer userId);

    UserDto get(Long id);

    List<UserDto> getAll();

    User getById(Long userId);

    List<User> getAllAlive();
}
