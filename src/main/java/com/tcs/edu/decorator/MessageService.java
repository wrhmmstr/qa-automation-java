package com.tcs.edu.decorator;

import static com.tcs.edu.decorator.PagingDecorator.messageToPage;
import static com.tcs.edu.decorator.SeverityDecorator.mapToString;
import static com.tcs.edu.decorator.TimestampMessageDecorator.*;
import static com.tcs.edu.printer.ConsolePrinter.print;
/**
 * Преобразование декорированного сообщения, уровня важности и разделителя в строку
 *
 * @author  t.m.kharchenko
 * @see     #processMessage(Severity, String...)  Метод для преобразования декорированного сообщения, уровня важности и разделителя в строку
 */
public class MessageService {
    /**
     * Метод преобразует декорированное сообщение, уровень важности и разделитель страницы в строку для вывода в консоль
     * и выводит полученную строку на печать.
     * Побочные эффекты пока отсутствуют.
     *
     * @param level     Перечислимый тип (переменная типа Severity) с уровнем важности сообщения
     * @param messages  Массив строк (String varargs) с сообщением для декорирования
     * @see             MessageService Родительский класс
     */
    public static void processMessage (Severity level, String... messages) {
        for (int currentMessage = 0; currentMessage < messages.length; currentMessage++) {
            print(String.format("%s %s %s", decorate(messages[currentMessage]), mapToString(level), messageToPage(messageCount)));
        }
    }
}
