package ru.xpendence.nightwatchobserver.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 02.03.19
 * Time: 15:31
 * e-mail: 2262288@gmail.com
 */
@Entity
@Table(name = "wall_posts")
@EqualsAndHashCode(callSuper = false)
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE wall_posts SET active = 0 WHERE id = ?")
@Where(clause = "active = 1")
public class WallPost extends AbstractEntity {

    private Integer postId;
    private Integer fromId;
    private Integer ownerId;
    private Integer date;
    private String text;
    private List<WallPostPhoto> photos;
    private User user;

    private WallPost(Integer postId, Integer fromId, Integer ownerId, Integer date, String text, User user) {
        this.postId = postId;
        this.fromId = fromId;
        this.ownerId = ownerId;
        this.date = date;
        this.text = text;
        this.user = user;
    }

    public static WallPost ofWallPostFull(Integer postId, Integer fromId, Integer ownerId, Integer date, String text, User user) {
        return new WallPost(postId, fromId, ownerId, date, text, user);
    }

    @Column(name = "post_id")
    public Integer getPostId() {
        return postId;
    }

    @Column(name = "from_id")
    public Integer getFromId() {
        return fromId;
    }

    @Column(name = "owner_id")
    public Integer getOwnerId() {
        return ownerId;
    }

    @Column(name = "date")
    public Integer getDate() {
        return date;
    }

    @Column(name = "text")
    public String getText() {
        return text;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "post", cascade = CascadeType.ALL)
    public List<WallPostPhoto> getPhotos() {
        return photos;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }
}
