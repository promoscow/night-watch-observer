package ru.xpendence.nightwatchobserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.xpendence.nightwatchobserver.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 28.02.19
 * Time: 15:22
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select * from users as u where u.active = 0 and u.id in " +
            "(select a.user_id from access_tokens as a where a.eternal = true or a.expires_in > CURRENT_TIMESTAMP)",
            nativeQuery = true
    )
    List<User> findAllAliveUsers();

    Optional<User> findByUserId(Integer userId);
}
