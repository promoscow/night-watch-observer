package ru.xpendence.nightwatchobserver.mapper.engine;

import ru.xpendence.nightwatchobserver.dto.AbstractDto;
import ru.xpendence.nightwatchobserver.entity.AbstractEntity;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 04.02.19
 * Time: 21:53
 * e-mail: 2262288@gmail.com
 */
public interface EntityDtoMapper<E extends AbstractEntity, D extends AbstractDto> {

    E toEntity(D dto);

    D toDto(E entity);
}
