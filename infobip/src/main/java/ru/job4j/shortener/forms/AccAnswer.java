package ru.job4j.shortener.forms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "ответ на запрос на получение нового аккаунта")
public class AccAnswer {
    @ApiModelProperty(value = "создан или нет")
    private boolean result;

    @ApiModelProperty(value = "описание результата")
    private String description;

    @ApiModelProperty(value = "сгенерированный пароль")
    private String password;
}
