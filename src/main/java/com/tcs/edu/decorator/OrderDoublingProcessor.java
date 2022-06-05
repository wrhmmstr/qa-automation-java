package com.tcs.edu.decorator;

import com.tcs.edu.MessageProcessor;
import com.tcs.edu.domain.Doubling;
import com.tcs.edu.domain.Message;
import com.tcs.edu.domain.MessageOrder;
import com.tcs.edu.service.ValidatingService;

/**
 * Процессинг массива сообщений в соответствии с заданным порядком и наличием дублей
 *
 * @author  t.m.kharchenko
 * @see #process(MessageOrder, Message...)              Метод для сортировки массива сообщений по заданному порядку
 * @see #process(MessageOrder, Doubling, Message...)    Метод для исключения дублей в массиве сообщений по заданному порядку
 */

public class OrderDoublingProcessor extends ValidatingService implements MessageProcessor {

    /**
     * Метод для сортировки массива сообщений по заданному порядку
     * @param order                 Перечислимый тип (переменная типа MessageOrder) с порядком сортировки
     * @param messages              Массив сообщений (переменная типа Message[])
     * @return                      Массив сообщений
     * @see OrderDoublingProcessor  Родительский класс
     */
    @Override
    public Message[] process (MessageOrder order, Message... messages) {
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

    /**
     * Перегруженный метод для определения дублей в отсортированном массиве сообщений
     * @param order                 Перечислимый тип (переменная типа MessageOrder) с порядком сортировки
     * @param doubling              Перечислимый тип (переменная типа Doubling) с определением наличия дублей
     * @param messages              Массив сообщений (переменная типа Message[])
     * @return                      Массив сообщений
     * @see OrderDoublingProcessor  Родительский класс
     */
    @Override
    public Message[] process(MessageOrder order, Doubling doubling, Message... messages) {
        super.isArgsValid(order, doubling);
        Message[] processedMessages = null;
        switch (doubling) {
            case DOUBLES: {
                processedMessages = process (order, messages);
                break;
            }
            case DISTINCT: {
                int currentMessageIndex = 0;
                int distinctWrittenMessageIndex = 0;
                Message[] processedMessagesWithEmpties = new Message[messages.length];
                Message[] orderedMessages = process(order, messages);
                processedMessagesWithEmpties[distinctWrittenMessageIndex] = orderedMessages[currentMessageIndex];
                for (currentMessageIndex = 0; currentMessageIndex < orderedMessages.length; currentMessageIndex++) {
                    Message currentMessage = orderedMessages[currentMessageIndex];
                    boolean isPrinted = false;
                    for (int distinctMessageIndex = 0; distinctMessageIndex <= distinctWrittenMessageIndex; distinctMessageIndex++) {
                        if (currentMessage.equals(processedMessagesWithEmpties[distinctMessageIndex])) {
                            isPrinted = true;
                            break;
                        }
                    }
                    if (!isPrinted && currentMessage.getMessage() != null) {
                        ++distinctWrittenMessageIndex;
                        processedMessagesWithEmpties[distinctWrittenMessageIndex] = currentMessage;
                    }
                }
                processedMessages = new Message[distinctWrittenMessageIndex + 1];
                for (int processedMessageIndex = 0; processedMessageIndex < processedMessages.length; processedMessageIndex++) {
                    processedMessages[processedMessageIndex] = processedMessagesWithEmpties[processedMessageIndex];
                }
                break;
            }
        }
        return processedMessages;
    }
}
