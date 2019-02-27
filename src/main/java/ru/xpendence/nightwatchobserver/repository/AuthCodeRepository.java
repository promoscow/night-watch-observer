package ru.xpendence.nightwatchobserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xpendence.nightwatchobserver.entity.AuthCode;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 27.02.19
 * Time: 21:05
 * e-mail: 2262288@gmail.com
 */
@Repository
public interface AuthCodeRepository extends JpaRepository<AuthCode, Long> {
}
