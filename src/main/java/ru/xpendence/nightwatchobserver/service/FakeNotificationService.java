package ru.xpendence.nightwatchobserver.service;

import ru.xpendence.nightwatchobserver.dto.ToRecognitionDto;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 03.03.19
 * Time: 10:22
 * e-mail: 2262288@gmail.com
 */
public interface FakeNotificationService {

    void postMessage(ToRecognitionDto dto);
}
