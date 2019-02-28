package ru.xpendence.nightwatchobserver.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 27.02.19
 * Time: 21:15
 * e-mail: 2262288@gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class AuthCodeDto extends AbstractDto {

    private String code;

    private AuthCodeDto(String code) {
        this.code = code;
    }

    public static AuthCodeDto of(String code) {
        return new AuthCodeDto(code);
    }
}
