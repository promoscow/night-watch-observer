package ru.xpendence.nightwatchobserver.entity;

import com.vk.api.sdk.client.actors.UserActor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

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
@SQLDelete(sql = "UPDATE users SET active = 0 WHERE id = ?")
@Where(clause = "active = 1")
public class User extends AbstractEntity {

    private Integer userId;
    private String email;
    private AccessToken accessToken;
    private AuthCode authCode;
    private List<WallPost> posts;

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

    public static User of(Integer userId, String email, AccessToken accessToken) {
        return new User(userId, email, accessToken);
    }

    @Column(name = "user_id", unique = true)
    public Integer getUserId() {
        return userId;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    public AccessToken getAccessToken() {
        return accessToken;
    }

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    public AuthCode getAuthCode() {
        return authCode;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    public List<WallPost> getPosts() {
        return posts;
    }
}
