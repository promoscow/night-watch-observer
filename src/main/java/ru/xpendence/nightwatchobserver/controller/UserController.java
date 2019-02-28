package ru.xpendence.nightwatchobserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.xpendence.nightwatchobserver.dto.UserDto;
import ru.xpendence.nightwatchobserver.service.UserService;

import java.util.List;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 28.02.19
 * Time: 17:10
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
