package ru.xpendence.nightwatchobserver.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 29.01.19
 * e-mail: 2262288@gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractDto implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    // TODO: 07.03.19 не сериализуется в паттерн
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    LocalDateTime created;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    LocalDateTime updated;
}
