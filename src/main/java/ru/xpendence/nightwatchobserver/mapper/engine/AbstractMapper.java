package ru.xpendence.nightwatchobserver.mapper.engine;

import lombok.Setter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.xpendence.nightwatchobserver.dto.AbstractDto;
import ru.xpendence.nightwatchobserver.entity.AbstractEntity;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 04.02.19
 * Time: 21:55
 * e-mail: 2262288@gmail.com
 */
@Setter
public abstract class AbstractMapper<E extends AbstractEntity, D extends AbstractDto> implements EntityDtoMapper<E, D> {

    @Autowired
    public ModelMapper mapper;

    private Class<E> entityClass;
    private Class<D> dtoClass;

    @PostConstruct
    public void init() {
    }

    @Override
    public E toEntity(D dto) {
        return Objects.isNull(dto)
                ? null
                : mapper.map(dto, entityClass);
    }

    @Override
    public D toDto(E entity) {
        return Objects.isNull(entity)
                ? null
                : mapper.map(entity, dtoClass);
    }

    public Converter<E, D> toDtoConverter() {
        return context -> {
            E source = context.getSource();
            D destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    public Converter<D, E> toEntityConverter() {
        return context -> {
            D source = context.getSource();
            E destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    public void mapSpecificFields(E source, D destination) {
    }

    public void mapSpecificFields(D source, E destination) {
    }
}
