package ru.xpendence.nightwatchobserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xpendence.nightwatchobserver.entity.User;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 28.02.19
 * Time: 15:22
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
