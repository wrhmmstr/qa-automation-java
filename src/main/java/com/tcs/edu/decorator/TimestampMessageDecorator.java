package com.tcs.edu.decorator;

import com.tcs.edu.printer.ConsolePrinter;

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
    private static int messageCount = 0;
    /**
     * Переменная с количеством строк на одной странице
     */
    public static int PAGE_SIZE = 2;

    /**
     * Метод добавляет к текущему времени сообщение через пробел с помощью конкатенации.
     * Побочные эффекты пока отсутствуют.
     *
     * @param message   Строка (переменная типа String) с сообщением для вывода
     * @return          Строка с декорированным сообщением из текущего времени, пробела и сообщения message
     * @see             TimestampMessageDecorator Родительский класс
     */
    public static String decorate(String message){
        ++messageCount;
        if (messageCount % PAGE_SIZE == 0) {
            final var decoratedMessage = String.format("%d %s %s %n---", messageCount, Instant.now().toString(), message);
            return decoratedMessage;
        } else {
            final var decoratedMessage = String.format("%d %s %s", messageCount, Instant.now().toString(), message);
            return decoratedMessage;
        }
    }
}
