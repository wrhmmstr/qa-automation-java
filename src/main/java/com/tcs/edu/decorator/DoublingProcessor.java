package com.tcs.edu.decorator;

import com.tcs.edu.MessageDoublingProcessor;
import com.tcs.edu.MessageOrderProcessor;
import com.tcs.edu.domain.Doubling;
import com.tcs.edu.domain.Message;
import com.tcs.edu.domain.MessageOrder;
import com.tcs.edu.service.ValidatingService;

public class DoublingProcessor extends ValidatingService implements MessageDoublingProcessor {

    private final MessageOrderProcessor messageOrderProcessor;

    public DoublingProcessor (MessageOrderProcessor messageOrderProcessor) {
        this.messageOrderProcessor = messageOrderProcessor;
    }

    /**
     * Перегруженный метод для определения дублей в отсортированном массиве сообщений
     * @param order                 Перечислимый тип (переменная типа MessageOrder) с порядком сортировки
     * @param doubling              Перечислимый тип (переменная типа Doubling) с определением наличия дублей
     * @param messages              Массив сообщений (переменная типа Message[])
     * @return                      Массив сообщений
     * @see OrderProcessor  Родительский класс
     */

    public Message[] process(MessageOrder order, Doubling doubling, Message... messages) {
        super.isArgsValid(order, doubling);
        Message[] processedMessages = null;
        switch (doubling) {
            case DOUBLES: {
                processedMessages = messageOrderProcessor.process (order, messages);
                break;
            }
            case DISTINCT: {
                int currentMessageIndex = 0;
                int distinctWrittenMessageIndex = 0;
                Message[] processedMessagesWithEmpties = new Message[messages.length];
                Message[] orderedMessages = messageOrderProcessor.process(order, messages);
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
