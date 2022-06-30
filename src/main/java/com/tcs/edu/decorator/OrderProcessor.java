package com.tcs.edu.decorator;

import com.tcs.edu.MessageOrderProcessor;
import com.tcs.edu.domain.Message;
import com.tcs.edu.domain.MessageOrder;
import com.tcs.edu.service.ValidatingService;

/**
 * Процессинг массива сообщений в соответствии с заданным порядком и наличием дублей
 *
 * @author  t.m.kharchenko
 * @see #process(MessageOrder, Message...)              Метод для сортировки массива сообщений по заданному порядку
 */

public class OrderProcessor extends ValidatingService implements MessageOrderProcessor {

    /**
     * Метод для сортировки массива сообщений по заданному порядку
     * @param order                 Перечислимый тип (переменная типа MessageOrder) с порядком сортировки
     * @param messages              Массив сообщений (переменная типа Message[])
     * @return                      Массив сообщений
     * @see OrderProcessor  Родительский класс
     */

    public Message[] process(MessageOrder order, Message... messages) {
        super.isArgsValid(order);
        Message[] processedMessages = new Message[messages.length];
        switch (order) {
            case ASC: {
                processedMessages = messages;
                break;
            }
            case DESC: {
                int currentDescMessage = 0;
                for (int currentMessage = messages.length - 1; currentMessage >= 0; currentMessage--) {
                    processedMessages[currentDescMessage] = messages[currentMessage];
                    currentDescMessage++;
                }
                break;
            }
        }
        return processedMessages;
    }


}
