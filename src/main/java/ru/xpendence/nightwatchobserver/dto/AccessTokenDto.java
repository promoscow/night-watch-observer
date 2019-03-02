package ru.xpendence.nightwatchobserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 28.02.19
 * Time: 15:26
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class AccessTokenDto extends AbstractDto {

    @JsonProperty(value = "access_token")
    private String accessToken;

    @JsonProperty(value = "expires_in")
    private LocalDateTime expiresIn;

    @JsonProperty(value = "user_id")
    private Long userId;

    @JsonProperty(value = "external")
    private Boolean external;
}
