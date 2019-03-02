package ru.xpendence.nightwatchobserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.xpendence.nightwatchobserver.dto.ToRecognitionDto;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 02.03.19
 * Time: 22:24
 * e-mail: 2262288@gmail.com
 */
@RestController
@RequestMapping("/recognition")
public class RecognitionMockController {

    @PostMapping
    public ResponseEntity<String> receive(@RequestBody ToRecognitionDto dto) {
        return ResponseEntity.ok("Successfully received!");
    }
}
