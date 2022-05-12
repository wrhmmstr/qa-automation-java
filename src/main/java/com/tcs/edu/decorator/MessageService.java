package com.tcs.edu.decorator;

import com.tcs.edu.domain.Message;

import java.util.Objects;

import static com.tcs.edu.decorator.PagingDecorator.messageToPage;
import static com.tcs.edu.decorator.SeverityDecorator.mapToString;
import static com.tcs.edu.decorator.TimestampMessageDecorator.*;
import static com.tcs.edu.printer.ConsolePrinter.print;

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
 * @see #processMessagesCycle(MessageOrder, Message...) Перегруженный метод преобразует декорированные сообщения из массива по примеру processMessagesCycle,
 * с возможностью сортировки по возрастанию и убыванию
 * @see #processPrintedMessages(int, int, Message, String[]) Метод проверяет текущее сообщение на вхождение в массив уже выведенных на на экран сообщений
 */
public class MessageService {
    /**
     * Метод преобразует непустое декорированное сообщение, уровень важности и разделитель страницы в строку для вывода в консоль
     * и выводит полученную строку на печать.
     * Если сообщение пустое, то преобразование не происходит. Если уровень важности пустой, то не участвует в преобразовании.
     *
     * @param message Строка (String) с сообщением для декорирования
     * @see MessageService Родительский класс
     */
//    public static void processMessage(Severity level, String message) {
//        if (message != null) {
//            if (level != null) {
//                print(String.format("%s %s %s", decorate(message), mapToString(level), messageToPage(messageCount)));
//            } else {
//                print(String.format("%s %s", decorate(message), messageToPage(messageCount)));
//            }
//        }
//    }
    public static void processMessage(Message message) {
        if (message.getMessage() != null) {
            if (message.getLevel() != null) {
                print(String.format("%s %s %s", decorate(message), mapToString(message.getLevel()), messageToPage(messageCount)));
            } else {
                print(String.format("%s %s", decorate(message), messageToPage(messageCount)));
            }
        }
    }

    /**
     * Метод преобразует декорированные сообщения из массива по примеру processMessage
     *
     * @param messages Массив строк (String varargs) с сообщениями для декорирования
     * @see MessageService Родительский класс
     */
    public static void processMessagesCycle(Message... messages) {
        for (int currentMessage = 0; currentMessage < messages.length; currentMessage++) {
            processMessage(messages[currentMessage]);
        }
    }

    /**
     * Перегруженный метод преобразует декорированные сообщения из массива по примеру processMessagesCycle,
     * с возможностью сортировки по возрастанию и убыванию
     *
     * @param order    Перечислимый тип (переменная типа MessageOrder) с порядком сортировки последовательности vararg
     * @param messages Массив строк (String varargs) с сообщениями для декорирования
     * @see MessageService Родительский класс
     */
    private static void processMessagesCycle(MessageOrder order, Message... messages) {
        switch (order) {
            case ASC: {
                processMessagesCycle(messages);
                break;
            }
            case DESC: {
                for (int currentMessage = messages.length - 1; currentMessage >= 0; currentMessage--) {
                    processMessage(messages[currentMessage]);
                }
                break;
            }
        }
    }

    /**
     * Метод преобразует декорированные сообщения по примеру processMessage
     *
     * @param message  Строка (String) с сообщением для декорирования
     * @param messages Массив строк (String varargs) с сообщениями для декорирования
     * @see MessageService Родительский класс
     */
    public static void processMessages(Message message, Message... messages) {
        processMessage(message);
        processMessagesCycle(messages);
    }

    /**
     * Перегруженный метод преобразует декорированные сообщения по примеру processMessage,
     * с возможностью обратной сортировки по последовательности vararg.
     *
     * @param order    Перечислимый тип (переменная типа MessageOrder) с порядком сортировки последовательности vararg
     * @param message  Строка (String) с сообщением для декорирования
     * @param messages Массив строк (String varargs) с сообщениями для декорирования
     * @see MessageService Родительский класс
     */
    public static void processMessages(MessageOrder order, Message message, Message... messages) {
        if (order != null) {
            switch (order) {
                case ASC: {
                    processMessages(message, messages);
                    break;
                }
                case DESC: {
                    processMessagesCycle(order, messages);
                    processMessage(message);
                    break;
                }
            }
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
     * @see MessageService Родительский класс
     */
    public static void processMessages(MessageOrder order, Doubling doubling, Message message, Message... messages) {
        if (doubling != null) {
            switch (doubling) {
                case DOUBLES: {
                    processMessages(order, message, messages);
                    break;
                }
                case DISTINCT: {
                    //условие задачи "по размеру равным длине последовательности vararg" нарушенно намеренно
                    //+ комментарий от преподавателя:
                    // "Не допускать при выводе дублей из всего множества входных параметров – сообщений.
                    //То есть на вход могут быть дубли сообщений в множестве String message + String… messages."
                    //в случае когда все сообщения будут непустыми в массиве нужно место для String message + String… messages.
                    if (order != null) {
                        String[] printedMessages = new String[messages.length + 1];
                        int printedWrittenMessageIndex = 0;
                        switch (order) {
                            case ASC: {
                                processMessage(message);
                                printedMessages[printedWrittenMessageIndex] = message.getMessage();
                                for (int currentMessageIndex = 0; currentMessageIndex < messages.length; currentMessageIndex++) {
                                    Message currentMessage = messages[currentMessageIndex];
                                    printedWrittenMessageIndex = processPrintedMessages(printedWrittenMessageIndex, currentMessageIndex, currentMessage, printedMessages);
                                }
                                break;
                            }
                            case DESC: {
                                for (int currentMessageIndex = messages.length - 1; currentMessageIndex >= 0; currentMessageIndex--) {
                                    Message currentMessage = messages[currentMessageIndex];
                                    printedWrittenMessageIndex = processPrintedMessages(printedWrittenMessageIndex, currentMessageIndex, currentMessage, printedMessages);
                                }
                                processPrintedMessages(printedWrittenMessageIndex, printedMessages.length, message, printedMessages);
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }
    }

    /**
     * Метод проверяет текущее сообщение на вхождение в массив уже выведенных на на экран сообщений.
     *
     * @param printedWrittenMessageIndex Целочисленный порядковый номер уникального сообщения выведенного на печать и записанного в массив
     * @param currentMessageIndex        Целочисленный порядковый номер текущего сообщения
     * @param currentMessage             Текущее сообщение
     * @param printedMessages            Массив выведенных на печать уникальных сообщений
     * @see MessageService Родительский класс
     */
    private static int processPrintedMessages(int printedWrittenMessageIndex, int currentMessageIndex, Message currentMessage, String[] printedMessages) {
        boolean isPrinted = false;
        for (int printedMessageIndex = 0; printedMessageIndex < printedMessages.length; printedMessageIndex++) {
//            if (Objects.equals(String.valueOf(currentMessage), printedMessages[printedMessageIndex])) {
            if (Objects.equals(currentMessage.getMessage(), printedMessages[printedMessageIndex])) {
                isPrinted = true;
                break;
            }
        }
        if (!isPrinted) {
            processMessage(currentMessage);
            ++printedWrittenMessageIndex;
            printedMessages[printedWrittenMessageIndex] = currentMessage.getMessage();
        }
        return printedWrittenMessageIndex;
    }
}