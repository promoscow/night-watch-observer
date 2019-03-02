package ru.xpendence.nightwatchobserver.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

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
@SQLDelete(sql = "UPDATE auth_codes SET active = 0 WHERE id = ?")
@Where(clause = "active = 1")
public class AuthCode extends AbstractEntity {

    private String code;
    private User user;

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    public User getUser() {
        return user;
    }
}
