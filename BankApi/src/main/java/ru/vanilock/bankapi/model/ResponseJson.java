package ru.vanilock.bankapi.model;

import lombok.Data;

/**
 * Класс для отправки ответов JSON
 */
@Data
public class ResponseJson {


    private final int value;  //значение или статус ответа (-1) - неудача, (1) - успешно
    private Object object; //значение или текст ошибки

    public ResponseJson(int value) {
        this.value = value;
    }

    public ResponseJson(int value, Object object) {
        this.value = value;
        this.object = object;
    }
}
