package ru.xpendence.nightwatchobserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xpendence.nightwatchobserver.entity.WallPostPhoto;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 02.03.19
 * Time: 16:22
 * e-mail: 2262288@gmail.com
 */
@Repository
public interface WallPostPhotoRepository extends JpaRepository<WallPostPhoto, Long> {
}
