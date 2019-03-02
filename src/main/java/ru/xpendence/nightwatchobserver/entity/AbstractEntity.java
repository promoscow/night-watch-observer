package ru.xpendence.nightwatchobserver.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.xpendence.nightwatchobserver.attributes.ActiveType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 07.02.19
 * e-mail: 2262288@gmail.com
 */
@MappedSuperclass
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractEntity implements Serializable {

    Long id;
    LocalDateTime created;
    LocalDateTime updated;
    ActiveType activeType = ActiveType.ENABLED;
    String error;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    @Column(name = "created", updatable = false)
    public LocalDateTime getCreated() {
        return created;
    }

    @Column(name = "updated", insertable = false)
    public LocalDateTime getUpdated() {
        return updated;
    }

    @Column(name = "error")
    public String getError() {
        return error;
    }

    @Column(name = "active")
    public ActiveType getActiveType() {
        return activeType;
    }

    @PrePersist
    public void toCreate() {
        setCreated(LocalDateTime.now());
    }

    @PreUpdate
    public void toUpdate() {
        setUpdated(LocalDateTime.now());
    }
}
