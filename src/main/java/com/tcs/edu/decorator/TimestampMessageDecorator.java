package com.tcs.edu.decorator;

import java.time.Instant;

/**
 * Декорирование сообщений с операцией добавления к строке текущего времени
 *
 * @author  t.m.kharchenko
 * @see     #decorate(String) Метод для добавления текущего времени к сообщению
 */

public class TimestampMessageDecorator {
    /**
     * Переменная с количеством выведенных сообщений
     */
    public static int messageCount = 0;
    /**
     * Метод добавляет порядковый номер и текущее время к сообщению через пробел с помощью форматирования.
     * Побочные эффекты пока отсутствуют.
     *
     * @param message   Строка (переменная типа String) с сообщением для вывода
     * @return          Строка с декорированным сообщением из текущего времени, пробела и сообщения message
     * @see             TimestampMessageDecorator Родительский класс
     */
    public static String decorate(String message){
        ++messageCount;
        final var decoratedMessage = String.format("%d %s %s", messageCount, Instant.now().toString(), message);
        return decoratedMessage;
    }
}
