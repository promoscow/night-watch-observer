package ru.xpendence.nightwatchobserver.service.api;

import ru.xpendence.nightwatchobserver.entity.User;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 26.02.19
 * Time: 22:55
 * e-mail: 2262288@gmail.com
 */
public interface ApiService {

    User authorize(String code);
}
