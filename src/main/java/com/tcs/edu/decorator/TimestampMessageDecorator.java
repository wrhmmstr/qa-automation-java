package com.tcs.edu.decorator;

import com.tcs.edu.MessageDecorator;

import java.time.Instant;

/**
 * Декорирование сообщений с операцией добавления к строке текущего времени
 *
 * @author  t.m.kharchenko
 * @see     #decorate(String) Метод для добавления текущего времени к сообщению
 */

public class TimestampMessageDecorator implements MessageDecorator {
    /**
     * Переменная с количеством выведенных сообщений
     */

    /**
     * Метод добавляет порядковый номер и текущее время к сообщению через пробел с помощью форматирования.
     * Побочные эффекты пока отсутствуют.
     *
     * @param message   Строка (переменная типа String) с сообщением для вывода
     * @return          Строка с декорированным сообщением из текущего времени, пробела и сообщения message
     * @see             TimestampMessageDecorator Родительский класс
     */
    @Override
    public String decorate(String message){
        final var decoratedMessage = String.format("%s %s", Instant.now().toString(), message);
        return decoratedMessage;
    }
}
