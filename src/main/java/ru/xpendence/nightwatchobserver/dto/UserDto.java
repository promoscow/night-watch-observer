package ru.xpendence.nightwatchobserver.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 28.02.19
 * Time: 15:25
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UserDto extends AbstractDto {

    private Integer userId;
    private AccessTokenDto accessToken;
    private AuthCodeDto authCode;
}
