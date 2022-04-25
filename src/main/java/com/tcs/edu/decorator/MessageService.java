package com.tcs.edu.decorator;

import java.util.Objects;

import static com.tcs.edu.decorator.PagingDecorator.messageToPage;
import static com.tcs.edu.decorator.SeverityDecorator.mapToString;
import static com.tcs.edu.decorator.TimestampMessageDecorator.*;
import static com.tcs.edu.printer.ConsolePrinter.print;
/**
 * Преобразование декорированного сообщения, уровня важности и разделителя в строку
 *
 * @author  t.m.kharchenko
 * @see     #processMessage(Severity, String) Метод для преобразования декорированного сообщения, уровня важности и разделителя в строку
 * @see     #processMessages(Severity, String, String...) Метод для преобразования декорированных сообщений по примеру processMessage
 * @see     #processMessages(Severity, MessageOrder, String, String...) Перегруженный метод для преобразования декорированных сообщений
 *          по примеру processMessage, с возможностью обратной сортировки по последовательности vararg.
 * @see     #processMessages(Severity, MessageOrder, Doubling, String, String...) Перегруженный метод для преобразования декорированных сообщений
 *          по примеру processMessage, с возможностью обратной сортировки по последовательности vararg, и возможностью удаления дублей сообщения.
 */
public class MessageService {
    /**
     * Метод преобразует непустое декорированное сообщение, уровень важности и разделитель страницы в строку для вывода в консоль
     * и выводит полученную строку на печать.
     * Если сообщение пустое, то преобразование не происходит. Если уровень важности пустой, то не участвует в преобразовании.
     * Побочные эффекты пока отсутствуют.
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
        } else ;
    }

    /**
     * Метод преобразует декорированные сообщения по примеру processMessage
     * Побочные эффекты пока отсутствуют.
     *
     * @param level    Перечислимый тип (переменная типа Severity) с уровнем важности сообщения
     * @param message  Строка (String) с сообщением для декорирования
     * @param messages Массив строк (String varargs) с сообщениями для декорирования
     * @see MessageService Родительский класс
     */
    public static void processMessages(Severity level, String message, String... messages) {
        processMessage(level, message);
        for (int currentMessage = 0; currentMessage < messages.length; currentMessage++) {
            processMessage(level, messages[currentMessage]);
        }
    }

    /**
     * Перегруженный метод преобразует декорированные сообщения по примеру processMessage,
     * с возможностью обратной сортировки по последовательности vararg.
     * Побочные эффекты пока отсутствуют.
     *
     * @param level    Перечислимый тип (переменная типа Severity) с уровнем важности сообщения
     * @param order    Перечислимый тип (переменная типа MessageOrder) с порядком сортировки последовательности vararg
     * @param message  Строка (String) с сообщением для декорирования
     * @param messages Массив строк (String varargs) с сообщениями для декорирования
     * @see MessageService Родительский класс
     */
    public static void processMessages(Severity level, MessageOrder order, String message, String... messages) {
        switch (order) {
            case ASC: {
                processMessages(level, message, messages);
                break;
            }
            case DESC: {
                for (int currentMessage = messages.length - 1; currentMessage >= 0; currentMessage--) {
                    processMessage(level, messages[currentMessage]);
                }
                processMessage(level, message);
                break;
            }
        }
    }

    /**
     * Перегруженный метод преобразует декорированные сообщения по примеру processMessage,
     * с возможностью обратной сортировки по последовательности vararg, и возможностью исключения дублей сообщений.
     * Побочные эффекты пока отсутствуют.
     *
     * @param level    Перечислимый тип (переменная типа Severity) с уровнем важности сообщения
     * @param order    Перечислимый тип (переменная типа MessageOrder) с порядком сортировки последовательности vararg
     * @param doubling Перечислимый тип (переменная типа Doubling) с признаком удаления дублей
     * @param message  Строка (String) с сообщением для декорирования
     * @param messages Массив строк (String varargs) с сообщениями для декорирования
     * @see MessageService Родительский класс
     */
    public static void processMessages(Severity level, MessageOrder order, Doubling doubling, String message, String... messages) {
        switch (doubling) {
            case DOUBLES: {
                processMessages(level, order, message, messages);
                break;
            }
            case DISTINCT: {
                String[] printedMessages = new String[messages.length+1];
                //условие задачи "по размеру равным длине последовательности vararg" нарушенно намеренно
                //+ комментарий от преподавателя:
                // "Не допускать при выводе дублей из всего множества входных параметров – сообщений.
                //То есть на вход могут быть дубли сообщений в множестве String message + String… messages."
                processMessage(level, message);
                int printedWritedMessage = 0;
                printedMessages[printedWritedMessage] = message;
                for (int currentMessage = 0; currentMessage < messages.length; currentMessage++) {
                    boolean isPrinted = false;
                    for (int printedMessage = 0; printedMessage < printedMessages.length; printedMessage++) {
                        if (Objects.equals(messages[currentMessage], printedMessages[printedMessage])) {
                            isPrinted = true;
                            break;
                        }
                    } if (messages[currentMessage] != null && !isPrinted) {
                            processMessage(level, messages[currentMessage]);
                        //processMessages(level, order, null, printedMessages);
                            ++printedWritedMessage;
                            printedMessages[printedWritedMessage] = messages[currentMessage];
                        }
                    }
                }
                break;
            }
        }
    }