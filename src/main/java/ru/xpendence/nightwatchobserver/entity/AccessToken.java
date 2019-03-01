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
    private Integer expiresIn;
    private User user;

    public AccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public AccessToken(String accessToken, Integer expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    @Column(name = "access_token")
    public String getAccessToken() {
        return accessToken;
    }

    @Column(name = "expires_in")
    public Integer getExpiresIn() {
        return expiresIn;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }
}
