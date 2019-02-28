package ru.xpendence.nightwatchobserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.xpendence.nightwatchobserver.dto.AccessTokenDto;
import ru.xpendence.nightwatchobserver.service.AccessTokenService;

import java.util.List;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 28.02.19
 * Time: 15:41
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@RestController
@RequestMapping(value = "/access_token")
public class AccessTokenController {

    private final AccessTokenService service;

    @Autowired
    public AccessTokenController(AccessTokenService service) {
        this.service = service;
    }

    @PostMapping(value = "/save")
    public ResponseEntity<AccessTokenDto> save(@RequestBody AccessTokenDto dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping(value = "/all")
    public List<AccessTokenDto> getAll() {
        return service.getAll();
    }
}
