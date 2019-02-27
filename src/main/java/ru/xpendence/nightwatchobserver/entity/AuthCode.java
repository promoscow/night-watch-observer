package ru.xpendence.nightwatchobserver.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 27.02.19
 * Time: 21:03
 * e-mail: 2262288@gmail.com
 */
@Entity
@Table(name = "auth_codes")
@EqualsAndHashCode(callSuper = false)
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthCode extends AbstractEntity {

    private String code;

    @Column(name = "code")
    public String getCode() {
        return code;
    }
}
