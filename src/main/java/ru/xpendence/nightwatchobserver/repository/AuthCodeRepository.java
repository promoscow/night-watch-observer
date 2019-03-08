package ru.xpendence.nightwatchobserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.xpendence.nightwatchobserver.entity.AuthCode;

import java.util.List;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 27.02.19
 * Time: 21:05
 * e-mail: 2262288@gmail.com
 */
@Repository
public interface AuthCodeRepository extends JpaRepository<AuthCode, Long> {

    @Modifying
    @Query(value = "update auth_codes as a set a.active = 0 " +
            "where a.active = 0 and a.user in (select users.id from users where users.user_id = :userId)",
            nativeQuery = true)
    @Transactional
    void deleteAllByUser(Integer userId);

    @Query(value = "select c.id from auth_codes as c where c.active = 0 and c.user in (select t.user_id from access_tokens as t)",
            nativeQuery = true)
    List<Long> findAllIdUsed();

    @Modifying
    @Transactional
    @Query(value = "update auth_codes as a set a.active = 0 where a.active = 0 and a.id in (:id)",
            nativeQuery = true)
    void deleteAllByIdIn(List<Long> id);

    AuthCode getAllByUserId(Long userId);
}