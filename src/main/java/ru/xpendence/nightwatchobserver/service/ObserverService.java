package ru.xpendence.nightwatchobserver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.xpendence.nightwatchobserver.service.api.ApiService;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 02.03.19
 * Time: 22:43
 * e-mail: 2262288@gmail.com
 */
@Component
@Slf4j
public class ObserverService {

    private final UserService userService;
    private final ApiService apiService;

    @Autowired
    public ObserverService(UserService userService,
                           ApiService apiService) {
        this.userService = userService;
        this.apiService = apiService;
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 60000)
    public void observe() {

        userService.getAllAlive()
                .forEach(u -> {
                    log.info("Observing user: {}", u.getId());
                    apiService.scan(u.getId());
                });
    }
}
