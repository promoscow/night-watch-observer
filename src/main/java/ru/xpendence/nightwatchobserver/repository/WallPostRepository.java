package ru.xpendence.nightwatchobserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xpendence.nightwatchobserver.entity.WallPost;

import java.util.List;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 02.03.19
 * Time: 16:21
 * e-mail: 2262288@gmail.com
 */
@Repository
public interface WallPostRepository extends JpaRepository<WallPost, Long> {

    List<WallPost> findAllByUserId(Long userId);
}
