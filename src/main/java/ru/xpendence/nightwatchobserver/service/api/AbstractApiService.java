package ru.xpendence.nightwatchobserver.service.api;

import com.vk.api.sdk.client.actors.UserActor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xpendence.nightwatchobserver.entity.AccessToken;
import ru.xpendence.nightwatchobserver.entity.User;
import ru.xpendence.nightwatchobserver.exception.UserException;
import ru.xpendence.nightwatchobserver.repository.UserRepository;
import ru.xpendence.nightwatchobserver.service.UserService;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 02.03.19
 * Time: 13:36
 * e-mail: 2262288@gmail.com
 */
@Component
@Slf4j
public abstract class AbstractApiService implements ApiService {

    private final UserRepository userRepository;
    final UserService userService;

    @Autowired
    public AbstractApiService(UserRepository userRepository,
                              UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public UserActor getActorByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserException(String.format("No such user with id: %d", userId))
        );
        if (!validateAccessToken(user.getAccessToken())) {
            return null;
        }
        return new UserActor(user.getUserId(), user.getAccessToken().getAccessToken());
    }

    public boolean validateAccessToken(AccessToken accessToken) {
        if (Objects.nonNull(accessToken.getExpiresIn()) && accessToken.getExpiresIn().isBefore(LocalDateTime.now())) {
            log.error("Access token expired for user {}", accessToken.getUser().getUserId());
            return false;
        }
        return true;
    }

    @Override
    public void checkIfUserExists(Integer userId, User user) {
        Optional<User> alreadyExistingUser = userService.getUserByUserId(userId);
        alreadyExistingUser.ifPresent(u -> user.setId(u.getId()));
    }

    @Override
    public void scanAll() {

    }
}
