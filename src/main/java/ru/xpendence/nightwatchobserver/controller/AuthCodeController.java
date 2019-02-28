package ru.xpendence.nightwatchobserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.xpendence.nightwatchobserver.dto.AuthCodeDto;
import ru.xpendence.nightwatchobserver.service.AuthCodeService;

import java.util.List;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 27.02.19
 * Time: 21:26
 * e-mail: 2262288@gmail.com
 */
@RestController
@RequestMapping("/code")
public class AuthCodeController {

    private final AuthCodeService service;

    @Autowired
    public AuthCodeController(AuthCodeService service) {
        this.service = service;
    }

    @GetMapping(value = "/save")
    public ResponseEntity<AuthCodeDto> save(@RequestParam(name = "code", required = false) String code) {
        return ResponseEntity.ok(service.save(AuthCodeDto.of(code)));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<AuthCodeDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
