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
     * Метод добавляет к текущему времени сообщение через пробел с помощью конкатенации.
     * Побочные эффекты пока отсутствуют.
     *
     * @param message   Строка (переменная типа String) с сообщением для вывода
     * @return          Строка с декорированным сообщением из текущего времени, проблеа и сообщения message
     * @see             TimestampMessageDecorator Родительский класс
     */
    public static String decorate(String message){
        return Instant.now() + " " + message;
    }
}
