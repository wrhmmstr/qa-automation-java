package com.tcs.edu.decorator;

import com.tcs.edu.MessageDecorator;
import com.tcs.edu.domain.Message;

import java.time.Instant;

/**
 * Декорирование сообщений с операцией добавления к строке текущего времени
 *
 * @author  t.m.kharchenko
 * @see     #decorate(Message) Метод для добавления текущего времени к сообщению
 * @see     #messageToPage(int) Метод для разделения выводимых сообщений на страницы
 */

public class TimestampPageMessageDecorator implements MessageDecorator {
    /**
     * Переменная с количеством выведенных сообщений
     */
    public static int messageCount = 0;
    private final int PAGE_SIZE = 2;
    /**
     * Метод добавляет порядковый номер и текущее время к сообщению через пробел с помощью форматирования.
     * Побочные эффекты пока отсутствуют.
     *
     * @param message   Строка (переменная типа String) с сообщением для вывода
     * @return          Строка с декорированным сообщением из текущего времени, пробела и сообщения message
     * @see             TimestampPageMessageDecorator Родительский класс
     */
    @Override
    public String decorate(Message message){
        ++messageCount;
        final var decoratedMessage = String.format("%d %s %s", messageCount, Instant.now().toString(), message.getMessage());
        return decoratedMessage;
    }

    /**
     * Метод добавляет разделитель страницы после строки кратной PAGE_SIZE
     * Побочные эффекты пока отсутствуют.
     *
     * @param messageCount  Число (переменная типа int) c порядковым номером выводимой строки
     * @return              Строка c разделителем страницы
     * @see                 TimestampPageMessageDecorator Родительский класс
     */
    public String messageToPage(int messageCount) {
        final String decoratedMessage;
        if (messageCount % PAGE_SIZE == 0) {
            decoratedMessage = String.format("%n---");
        } else {
            decoratedMessage = String.format("");
        }
        return decoratedMessage;
    }
}
