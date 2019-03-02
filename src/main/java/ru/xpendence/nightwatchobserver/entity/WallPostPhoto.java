package ru.xpendence.nightwatchobserver.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 02.03.19
 * Time: 15:51
 * e-mail: 2262288@gmail.com
 */
@Entity
@Table(name = "wall_post_photos")
@EqualsAndHashCode(callSuper = false)
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE wall_post_photos SET active = 0 WHERE id = ?")
@Where(clause = "active = 1")
public class WallPostPhoto extends AbstractEntity {

    private Integer photoId;
    private String photo604Url;
    private WallPost post;

    private WallPostPhoto(Integer photoId, String photo604Url, WallPost post) {
        this.photoId = photoId;
        this.photo604Url = photo604Url;
        this.post = post;
    }

    public static WallPostPhoto of(Integer photoId, String photo604Url, WallPost post) {
        return new WallPostPhoto(photoId, photo604Url, post);
    }

    @Column(name = "photo_id")
    public Integer getPhotoId() {
        return photoId;
    }

    @Column(name = "photo_url")
    public String getPhoto604Url() {
        return photo604Url;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    public WallPost getPost() {
        return post;
    }
}
