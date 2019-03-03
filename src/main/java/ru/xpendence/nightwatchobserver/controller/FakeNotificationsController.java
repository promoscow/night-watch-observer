package ru.xpendence.nightwatchobserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.xpendence.nightwatchobserver.dto.ToRecognitionDto;
import ru.xpendence.nightwatchobserver.service.FakeNotificationService;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 03.03.19
 * Time: 12:04
 * e-mail: 2262288@gmail.com
 */
@RestController
@RequestMapping("/notify")
public class FakeNotificationsController {

    private final FakeNotificationService service;

    @Autowired
    public FakeNotificationsController(FakeNotificationService service) {
        this.service = service;
    }

    @PostMapping
    public void notify(@RequestBody ToRecognitionDto dto) {
        service.postMessage(dto);
    }
}
