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
    private String text;

    private ToRecognitionDto(Long externalId, String url, Long postId, Integer authorId, String text) {
        this.externalId = externalId;
        this.url = url;
        this.postId = postId;
        this.authorId = authorId;
        this.text = text;
    }

    public static ToRecognitionDto of(Long externalId, String url, Long postId, Integer authorId, String text) {
        return new ToRecognitionDto(externalId, url, postId, authorId, text);
    }
}
