package com.tcs.edu.decorator;

import com.tcs.edu.MessageDecorator;
import com.tcs.edu.domain.Message;

import java.time.Instant;

/**
 * Декорирование сообщений с операцией добавления к строке текущего времени
 *
 * @author  t.m.kharchenko
 * @see     #decorate(Message) Метод для добавления текущего времени к сообщению
 */

public class TimestampMessageDecorator implements MessageDecorator {
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
    @Override
    public  String decorate(Message message){
        ++messageCount;
        final var decoratedMessage = String.format("%d %s %s", messageCount, Instant.now().toString(), message.getMessage());
        return decoratedMessage;
    }
}
