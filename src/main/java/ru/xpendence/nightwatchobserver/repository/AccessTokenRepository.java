package ru.xpendence.nightwatchobserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.xpendence.nightwatchobserver.entity.AccessToken;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 28.02.19
 * Time: 15:22
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {

    List<AccessToken> findAllByEternalOrExpiresInAfter(boolean eternal, LocalDateTime expiresIn);

    Integer deleteAllByEternalFalseAndExpiresInBefore(LocalDateTime localDateTime);

    // TODO: 07.03.19 протестировать удаление на тестовой базе
    @Modifying
    @Query(value = "update access_tokens as a set a.active = 0 " +
            "where a.user_id in (select users.id from users where users.user_id = :userId)",
            nativeQuery = true)
    @Transactional
    void deleteAllByUserId(Integer userId);

    AccessToken getByUserId(Long userId);
}
