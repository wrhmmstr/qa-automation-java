package com.tcs.edu.service;

import com.tcs.edu.*;
import com.tcs.edu.domain.*;
import com.tcs.edu.repository.HashMapMessageRepository;
import com.tcs.edu.repository.MessageRepository;

import java.util.Collection;
import java.util.UUID;

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

//    private final Printer printer;
    private final MessageProcessor messageProcessor;
    private final MessageDecorator[] decorators;
    private MessageRepository messageRepository = new HashMapMessageRepository();

    public DecoratingMessageService(/*Printer printer, */MessageRepository messageRepository, MessageProcessor messageProcessor, MessageDecorator... decorators) {
//        this.printer = printer;
        this.messageRepository = messageRepository;
        this.messageProcessor = messageProcessor;
        this.decorators = decorators;
        }

    /**
     * Метод преобразует непустое декорированное сообщение, уровень важности и разделитель страницы в строку для вывода в консоль
     * и выводит полученную строку на печать.
     * Если сообщение пустое, то преобразование не происходит. Если уровень важности пустой, то не участвует в преобразовании.
     *
     * @param message                   Строка (String) с сообщением для декорирования
     * @see DecoratingMessageService    Родительский класс
     * @return                          UUID сообщения в коллекции
     */
    public UUID processMessage(Message message) throws LogException {
        try {
            DecoratedMessage decoratedMessage;
            String processedMessage;
            super.isArgsValid(message);
                    processedMessage = String.format("%s %s", message.getMessage(), message.getLevel().getSeverity());
                    for (MessageDecorator decorator : decorators) {
                        processedMessage = decorator.decorate(processedMessage);
                    }
                decoratedMessage = new DecoratedMessage(message.getLevel(), processedMessage, null);
                messageRepository.create(decoratedMessage);
//                    printer.print(processedMessage);
            return decoratedMessage.getId();
        } catch (IllegalArgumentException e) {
            throw new LogException("Argument is invalid!", e);
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
        super.isArgsValid(message);
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
    public void processMessagesCycle(Message... messages) throws LogException {
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
    public void processMessages(Message message, Message... messages) throws LogException {
        try {
            processMessagesCycle(combineMessages(message, messages));
        } catch (IllegalArgumentException e) {
            throw new LogException("Argument is invalid!", e);
        }
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
    public void processMessages(MessageOrder order, Message message, Message... messages) throws LogException {
        try {
            processMessagesCycle(messageProcessor.process(order, combineMessages(message, messages)));
        } catch(IllegalArgumentException e) {
            throw new LogException("Argument is invalid!", e);
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
    public void processMessages(MessageOrder order, Doubling doubling, Message message, Message... messages) throws LogException {
        try {
            processMessagesCycle(messageProcessor.process(order, doubling, combineMessages(message, messages)));
        } catch(IllegalArgumentException e) {
            throw new LogException("Argument is invalid!", e);
        }
    }

    @Override
    public DecoratedMessage findByPrimaryKey(UUID key) {
        return messageRepository.findByPrimaryKey(key);
    }

    @Override
    public Collection<DecoratedMessage> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Collection<DecoratedMessage> findBySeverity(Severity by) {
        return messageRepository.findBySeverity(by);
    }


}