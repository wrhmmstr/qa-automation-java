package com.tcs.edu.decorator;

import java.util.Objects;

import static com.tcs.edu.decorator.PagingDecorator.messageToPage;
import static com.tcs.edu.decorator.SeverityDecorator.mapToString;
import static com.tcs.edu.decorator.TimestampMessageDecorator.*;
import static com.tcs.edu.printer.ConsolePrinter.print;

/**
 * Преобразование декорированного сообщения, уровня важности и разделителя в строку
 *
 * @author t.m.kharchenko
 * @see #processMessage(Severity, String) Метод для преобразования декорированного сообщения, уровня важности и разделителя в строку
 * @see #processMessages(Severity, String, String...) Метод для преобразования декорированных сообщений по примеру processMessage
 * @see #processMessages(Severity, MessageOrder, String, String...) Перегруженный метод для преобразования декорированных сообщений
 * по примеру processMessage, с возможностью обратной сортировки по последовательности vararg.
 * @see #processMessages(Severity, MessageOrder, Doubling, String, String...) Перегруженный метод для преобразования декорированных сообщений
 * по примеру processMessage, с возможностью обратной сортировки по последовательности vararg, и возможностью удаления дублей сообщения.
 * @see #processMessagesCycle(Severity, String...) Метод преобразует декорированные сообщения из массива по примеру processMessage
 * @see #processMessagesCycle(Severity, MessageOrder, String...) Перегруженный метод преобразует декорированные сообщения из массива по примеру processMessagesCycle,
 * с возможностью сортировки по возрастанию и убыванию
 * @see #processPrintedMessages(Severity, int, int, String, String[]) Метод проверяет текущее сообщение на вхождение в массив уже выведенных на на экран сообщений
 */
public class MessageService {
    /**
     * Метод преобразует непустое декорированное сообщение, уровень важности и разделитель страницы в строку для вывода в консоль
     * и выводит полученную строку на печать.
     * Если сообщение пустое, то преобразование не происходит. Если уровень важности пустой, то не участвует в преобразовании.
     *
     * @param level   Перечислимый тип (переменная типа Severity) с уровнем важности сообщения
     * @param message Строка (String) с сообщением для декорирования
     * @see MessageService Родительский класс
     */
    public static void processMessage(Severity level, String message) {
        if (message != null) {
            if (level != null) {
                print(String.format("%s %s %s", decorate(message), mapToString(level), messageToPage(messageCount)));
            } else {
                print(String.format("%s %s", decorate(message), messageToPage(messageCount)));
            }
        }
    }

    /**
     * Метод преобразует декорированные сообщения из массива по примеру processMessage
     *
     * @param level    Перечислимый тип (переменная типа Severity) с уровнем важности сообщения
     * @param messages Массив строк (String varargs) с сообщениями для декорирования
     * @see MessageService Родительский класс
     */
    private static void processMessagesCycle(Severity level, String... messages) {
        for (int currentMessage = 0; currentMessage < messages.length; currentMessage++) {
            processMessage(level, messages[currentMessage]);
        }
    }

    /**
     * Перегруженный метод преобразует декорированные сообщения из массива по примеру processMessagesCycle,
     * с возможностью сортировки по возрастанию и убыванию
     *
     * @param level    Перечислимый тип (переменная типа Severity) с уровнем важности сообщения
     * @param order    Перечислимый тип (переменная типа MessageOrder) с порядком сортировки последовательности vararg
     * @param messages Массив строк (String varargs) с сообщениями для декорирования
     * @see MessageService Родительский класс
     */
    private static void processMessagesCycle(Severity level, MessageOrder order, String... messages) {
        switch (order) {
            case ASC: {
                processMessagesCycle(level, messages);
                break;
            }
            case DESC: {
                for (int currentMessage = messages.length - 1; currentMessage >= 0; currentMessage--) {
                    processMessage(level, messages[currentMessage]);
                }
                break;
            }
        }
    }

    /**
     * Метод преобразует декорированные сообщения по примеру processMessage
     *
     * @param level    Перечислимый тип (переменная типа Severity) с уровнем важности сообщения
     * @param message  Строка (String) с сообщением для декорирования
     * @param messages Массив строк (String varargs) с сообщениями для декорирования
     * @see MessageService Родительский класс
     */
    public static void processMessages(Severity level, String message, String... messages) {
        processMessage(level, message);
        processMessagesCycle(level, messages);
    }

    /**
     * Перегруженный метод преобразует декорированные сообщения по примеру processMessage,
     * с возможностью обратной сортировки по последовательности vararg.
     *
     * @param level    Перечислимый тип (переменная типа Severity) с уровнем важности сообщения
     * @param order    Перечислимый тип (переменная типа MessageOrder) с порядком сортировки последовательности vararg
     * @param message  Строка (String) с сообщением для декорирования
     * @param messages Массив строк (String varargs) с сообщениями для декорирования
     * @see MessageService Родительский класс
     */
    public static void processMessages(Severity level, MessageOrder order, String message, String... messages) {
        if (order != null) {
            switch (order) {
                case ASC: {
                    processMessages(level, message, messages);
                    break;
                }
                case DESC: {
                    processMessagesCycle(level, order, messages);
                    processMessage(level, message);
                    break;
                }
            }
        }
    }

    /**
     * Перегруженный метод преобразует декорированные сообщения по примеру processMessage,
     * с возможностью обратной сортировки по последовательности vararg, и возможностью исключения дублей сообщений.
     *
     * @param level    Перечислимый тип (переменная типа Severity) с уровнем важности сообщения
     * @param order    Перечислимый тип (переменная типа MessageOrder) с порядком сортировки последовательности vararg
     * @param doubling Перечислимый тип (переменная типа Doubling) с признаком наличия дублей
     * @param message  Строка (String) с сообщением для декорирования
     * @param messages Массив строк (String varargs) с сообщениями для декорирования
     * @see MessageService Родительский класс
     */
    public static void processMessages(Severity level, MessageOrder order, Doubling doubling, String message, String... messages) {
        if (doubling != null) {
            switch (doubling) {
                case DOUBLES: {
                    processMessages(level, order, message, messages);
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
                                processMessage(level, message);
                                printedMessages[printedWrittenMessageIndex] = message;
                                for (int currentMessageIndex = 0; currentMessageIndex < messages.length; currentMessageIndex++) {
                                    String currentMessage = messages[currentMessageIndex];
                                    printedWrittenMessageIndex = processPrintedMessages(level, printedWrittenMessageIndex, currentMessageIndex, currentMessage, printedMessages);
                                }
                                break;
                            }
                            case DESC: {
                                for (int currentMessageIndex = messages.length - 1; currentMessageIndex >= 0; currentMessageIndex--) {
                                    String currentMessage = messages[currentMessageIndex];
                                    printedWrittenMessageIndex = processPrintedMessages(level, printedWrittenMessageIndex, currentMessageIndex, currentMessage, printedMessages);
                                }
                                processPrintedMessages(level, printedWrittenMessageIndex, printedMessages.length - 1, message, printedMessages);
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
     * @param level                      Перечислимый тип (переменная типа Severity) с уровнем важности сообщения
     * @param printedWrittenMessageIndex Целочисленный порядковый номер уникального сообщения выведенного на печать и записанного в массив
     * @param currentMessageIndex        Целочисленный порядковый номер текущего сообщения
     * @param currentMessage             Текущее сообщение
     * @param printedMessages            Массив выведенных на печать уникальных сообщений
     * @see MessageService Родительский класс
     */
    private static int processPrintedMessages(Severity level, int printedWrittenMessageIndex, int currentMessageIndex, String currentMessage, String[] printedMessages) {
        boolean isPrinted = false;
        for (int printedMessageIndex = 0; printedMessageIndex <= currentMessageIndex; printedMessageIndex++) {
            if (Objects.equals(currentMessage, printedMessages[printedMessageIndex])) {
                isPrinted = true;
                break;
            }
        }
        if (!isPrinted) {
            processMessage(level, currentMessage);
            ++printedWrittenMessageIndex;
            printedMessages[printedWrittenMessageIndex] = currentMessage;
        }
        return printedWrittenMessageIndex;
    }
}