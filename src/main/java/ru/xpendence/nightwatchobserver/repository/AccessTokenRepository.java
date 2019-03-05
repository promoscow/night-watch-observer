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

    void deleteAllByEternalFalseAndExpiresInBefore(LocalDateTime localDateTime);

    @Modifying
    @Query(value = "delete from access_tokens where access_tokens.user_id in (select users.id from users where users.user_id = :userId)",
            nativeQuery = true)
    @Transactional
    void deleteAllByUserId(Integer userId);
}
