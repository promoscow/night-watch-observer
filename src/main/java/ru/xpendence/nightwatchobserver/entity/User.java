package ru.xpendence.nightwatchobserver.entity;

import com.vk.api.sdk.client.actors.UserActor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 28.02.19
 * Time: 15:16
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = false)
@Setter
@NoArgsConstructor
public class User extends AbstractEntity {

    private Integer userId;
    private String email;
    private AccessToken accessToken;

    private User(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    private User(Integer userId, AccessToken accessToken) {
        this.userId = userId;
        this.accessToken = accessToken;
    }

    private User(Integer userId, String email, AccessToken accessToken) {
        this.userId = userId;
        this.email = email;
        this.accessToken = accessToken;
    }

    public static User ofAccessToken(AccessToken accessToken) {
        return new User(accessToken);
    }

    public static User ofActor(UserActor actor) {
        AccessToken accessToken = new AccessToken(actor.getAccessToken());
        return new User(actor.getId(), accessToken);
    }

    public static User of(Integer userId, String email, String accessToken, Integer expiresIn) {
        return new User(userId, email, new AccessToken(accessToken, expiresIn));
    }

    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }
}
