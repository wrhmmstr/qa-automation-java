package com.tcs.edu.service;

import com.tcs.edu.*;
import com.tcs.edu.domain.Doubling;
import com.tcs.edu.domain.Message;
import com.tcs.edu.domain.MessageOrder;

/**
 * Преобразование декорированного сообщения, уровня важности и разделителя в строку
 *
 * @author t.m.kharchenko
 * @see #processMessage(Message) Метод для преобразования декорированного сообщения, уровня важности и разделителя в строку
 * @see #processMessages(Message, Message...) Метод для преобразования декорированных сообщений по примеру processMessage
 * @see #processMessages(MessageOrder, Message, Message...) Перегруженный метод для преобразования декорированных сообщений
 * по примеру processMessage, с возможностью обратной сортировки по последовательности vararg.
 * @see #processMessages(MessageOrder, Doubling, Message, Message...) Перегруженный метод для преобразования декорированных сообщений
 * по примеру processMessage, с возможностью обратной сортировки по последовательности vararg, и возможностью удаления дублей сообщения.
 * @see #processMessagesCycle(Message...) Метод преобразует декорированные сообщения из массива по примеру processMessage
 * @see #combineMessages(Message, Message...) Метод преобразует сообщение и варарг сообщений в единый массив
 */
public class DecoratingMessageService extends ValidatingService implements MessageService {

    private final Printer printer;
    private final MessageProcessor messageProcessor;
    private final MessageDecorator[] decorators;

    public DecoratingMessageService(Printer printer, MessageProcessor messageProcessor, MessageDecorator... decorators) {
        this.printer = printer;
        this.messageProcessor = messageProcessor;
        this.decorators = decorators;
        }

    /**
     * Метод преобразует непустое декорированное сообщение, уровень важности и разделитель страницы в строку для вывода в консоль
     * и выводит полученную строку на печать.
     * Если сообщение пустое, то преобразование не происходит. Если уровень важности пустой, то не участвует в преобразовании.
     *
     * @param message Строка (String) с сообщением для декорирования
     * @see DecoratingMessageService Родительский класс
     */
    public void processMessage(Message message) {
        if (super.isArgsValid(message)) {
            try {
                String processedMessage;
                if (super.isArgsValid(message.getLevel())) {
                    processedMessage = String.format("%s %s", message.getMessage(), message.getLevel().getSeverity());
                    for (MessageDecorator decorator : decorators) {
                        processedMessage = decorator.decorate(processedMessage);
                    }
                } else {
                    processedMessage = message.getMessage();
                    for (MessageDecorator decorator : decorators) {
                        processedMessage = decorator.decorate(processedMessage);
                    }
                }
                printer.print(processedMessage);
            } catch(IllegalArgumentException e) {
                throw new ExceptionLogger("Argument is invalid!", e);
            }
        }
    }

    /**
     * Метод комбинирует сообщение и массив сообщений в единый массив
     *
     * @param message   Строка (String) с сообщением для декорирования
     * @param messages  Массив строк (String...) с сообщениями для декорирования
     * @see DecoratingMessageService Родительский класс
     */
    public Message[] combineMessages(Message message, Message... messages) {
        Message[] combinedMessages = new Message[messages.length+1];
        int currentMessage = 0;
        combinedMessages[currentMessage] = message;
        for (currentMessage = 1; currentMessage < combinedMessages.length; currentMessage++) {
            combinedMessages[currentMessage] = messages[currentMessage-1];
        }
        return combinedMessages;
    }

    /**
     * Метод преобразует декорированные сообщения из массива по примеру processMessage
     *
     * @param messages Массив строк (String varargs) с сообщениями для декорирования
     * @see DecoratingMessageService Родительский класс
     */
    public void processMessagesCycle(Message... messages) {
        for (int currentMessage = 0; currentMessage < messages.length; currentMessage++) {
            processMessage(messages[currentMessage]);
        }
    }

    /**
     * Метод преобразует декорированные сообщения по примеру processMessage
     *
     * @param message  Строка (String) с сообщением для декорирования
     * @param messages Массив строк (String varargs) с сообщениями для декорирования
     * @see DecoratingMessageService Родительский класс
     */
    public void processMessages(Message message, Message... messages) {
        processMessagesCycle(combineMessages(message, messages));
    }

    /**
     * Перегруженный метод преобразует декорированные сообщения по примеру processMessage,
     * с возможностью обратной сортировки по последовательности vararg.
     *
     * @param order    Перечислимый тип (переменная типа MessageOrder) с порядком сортировки последовательности vararg
     * @param message  Строка (String) с сообщением для декорирования
     * @param messages Массив строк (String varargs) с сообщениями для декорирования
     * @see DecoratingMessageService Родительский класс
     */
    public void processMessages(MessageOrder order, Message message, Message... messages) {
        try {
            processMessagesCycle(messageProcessor.process(order, combineMessages(message, messages)));
        } catch(IllegalArgumentException e) {
            throw new ExceptionLogger("Argument is invalid!", e);
        }
    }

    /**
     * Перегруженный метод преобразует декорированные сообщения по примеру processMessage,
     * с возможностью обратной сортировки по последовательности vararg, и возможностью исключения дублей сообщений.
     *
     * @param order    Перечислимый тип (переменная типа MessageOrder) с порядком сортировки последовательности vararg
     * @param doubling Перечислимый тип (переменная типа Doubling) с признаком наличия дублей
     * @param message  Строка (String) с сообщением для декорирования
     * @param messages Массив строк (String varargs) с сообщениями для декорирования
     * @see DecoratingMessageService Родительский класс
     */
    public void processMessages(MessageOrder order, Doubling doubling, Message message, Message... messages) {
        try {
            processMessagesCycle(messageProcessor.process(order, doubling, combineMessages(message, messages)));
        } catch(IllegalArgumentException e) {
            throw new ExceptionLogger("Argument is invalid!", e);
        }
    }
}