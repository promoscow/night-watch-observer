package ru.xpendence.nightwatchobserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.xpendence.nightwatchobserver.dto.AccessTokenDto;
import ru.xpendence.nightwatchobserver.dto.AuthCodeDto;
import ru.xpendence.nightwatchobserver.service.AccessTokenService;
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
    private final AccessTokenService accessTokenService;

    @Autowired
    public AuthCodeController(AuthCodeService service,
                              AccessTokenService accessTokenService) {
        this.service = service;
        this.accessTokenService = accessTokenService;
    }

    @GetMapping(value = "/save")
    public ResponseEntity<AuthCodeDto> save(@RequestParam(name = "code", required = false) String code) {
        return ResponseEntity.ok(service.save(AuthCodeDto.of(code)));
    }

    @PostMapping(value = "/save")
    public ResponseEntity<AccessTokenDto> save(@RequestBody AccessTokenDto dto) {
        return ResponseEntity.ok(accessTokenService.save(dto));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<AuthCodeDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
