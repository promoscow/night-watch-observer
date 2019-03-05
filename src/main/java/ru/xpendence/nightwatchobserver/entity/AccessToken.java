package ru.xpendence.nightwatchobserver.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.Clock;
import java.time.LocalDateTime;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 28.02.19
 * Time: 15:11
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@Entity
@Table(name = "access_tokens")
@EqualsAndHashCode(callSuper = false)
@Where(clause = "active = 1")
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE access_tokens SET active = 0 WHERE id = ?")
public class AccessToken extends AbstractEntity {

    private String accessToken;
    private LocalDateTime expiresIn;
    private User user;
    private Boolean eternal;

    public AccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public AccessToken(String accessToken, Integer expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = LocalDateTime.now(Clock.systemUTC()).plusSeconds(expiresIn.longValue());
    }

    @Column(name = "access_token")
    public String getAccessToken() {
        return accessToken;
    }

    @Column(name = "expires_in")
    public LocalDateTime getExpiresIn() {
        return expiresIn;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    @Column(name = "eternal")
    public Boolean getEternal() {
        return eternal;
    }
}
