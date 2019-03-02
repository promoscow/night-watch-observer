package ru.xpendence.nightwatchobserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.xpendence.nightwatchobserver.service.api.ApiService;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 02.03.19
 * Time: 11:46
 * e-mail: 2262288@gmail.com
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    private final ApiService service;

    @Autowired
    public ApiController(ApiService service) {
        this.service = service;
    }

    @GetMapping(value = "/wall")
    public Boolean scan(@RequestParam Long userId) {
        return service.scan(userId);
    }

}
