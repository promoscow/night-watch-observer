package ru.xpendence.nightwatchobserver.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 02.03.19
 * Time: 21:35
 * e-mail: 2262288@gmail.com
 */
@Data
@NoArgsConstructor
public class ToRecognitionDto {

    private Long externalId;
    private String url;
    private Long postId;
    private Integer authorId;

    private ToRecognitionDto(Long externalId, String url, Long postId, Integer authorId) {
        this.externalId = externalId;
        this.url = url;
        this.postId = postId;
        this.authorId = authorId;
    }

    public static ToRecognitionDto of(Long externalId, String url, Long postId, Integer authorId) {
        return new ToRecognitionDto(externalId, url, postId, authorId);
    }
}
