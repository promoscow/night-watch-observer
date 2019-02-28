package ru.xpendence.nightwatchobserver.entity;

import com.google.common.collect.Lists;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class User extends AbstractEntity {

    private List<AccessToken> accessTokens;

    private User(List<AccessToken> accessTokens) {
        this.accessTokens = accessTokens;
    }

    public static User of(AccessToken accessToken) {
        return new User(Lists.newArrayList(accessToken));
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    public List<AccessToken> getAccessTokens() {
        return accessTokens;
    }
}
