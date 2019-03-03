package ru.xpendence.nightwatchobserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
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

    List<AccessToken> findAllByExternalOrExpiresInAfter(boolean external, LocalDateTime expiresIn);

    void deleteAllByExternalFalseAndExpiresInBefore(LocalDateTime localDateTime);
}
