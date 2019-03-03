package ru.xpendence.nightwatchobserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.xpendence.nightwatchobserver.entity.WallPost;
import ru.xpendence.nightwatchobserver.exception.WallPostException;
import ru.xpendence.nightwatchobserver.repository.WallPostRepository;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 03.03.19
 * Time: 10:26
 * e-mail: 2262288@gmail.com
 */
@Service
public class WallPostServiceImpl implements WallPostService {

    private final WallPostRepository repository;

    @Autowired
    public WallPostServiceImpl(WallPostRepository repository) {
        this.repository = repository;
    }

    @Override
    public WallPost getById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new WallPostException(String.format("Unable to get post by id %d", id))
        );
    }
}
