package ru.xpendence.nightwatchobserver.service;

import ru.xpendence.nightwatchobserver.entity.WallPost;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 03.03.19
 * Time: 10:26
 * e-mail: 2262288@gmail.com
 */
public interface WallPostService {

    WallPost getById(Long id);
}
