package ru.xpendence.nightwatchobserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.xpendence.nightwatchobserver.entity.AuthCode;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 27.02.19
 * Time: 21:05
 * e-mail: 2262288@gmail.com
 */
@Repository
public interface AuthCodeRepository extends JpaRepository<AuthCode, Long> {

    @Modifying
    @Query(value = "delete from auth_codes where auth_codes.user in (select users.id from users where users.user_id = :userId)",
            nativeQuery = true)
    @Transactional
    void deleteAllByUser(Integer userId);

    AuthCode getAllByUserId(Long userId);
}