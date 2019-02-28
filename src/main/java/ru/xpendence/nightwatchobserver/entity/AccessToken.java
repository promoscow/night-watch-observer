package ru.xpendence.nightwatchobserver.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 28.02.19
 * Time: 15:11
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@Entity
@Table(name = "access_tokens")
@EqualsAndHashCode(callSuper = false)
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessToken extends AbstractEntity {

    private String accessToken;
    private Long expiresIn;
    private User user;

    @Column(name = "access_token")
    public String getAccessToken() {
        return accessToken;
    }

    @Column(name = "expires_in")
    public Long getExpiresIn() {
        return expiresIn;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }
}
