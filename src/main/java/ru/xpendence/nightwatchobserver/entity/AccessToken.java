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

    public AccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Column(name = "access_token")
    public String getAccessToken() {
        return accessToken;
    }

    @Column(name = "expires_in")
    public Integer getExpiresIn() {
        return expiresIn;
    }
}
