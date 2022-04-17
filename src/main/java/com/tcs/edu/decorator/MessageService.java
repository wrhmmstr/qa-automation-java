package com.tcs.edu.decorator;

import static com.tcs.edu.decorator.PagingDecorator.messageToPage;
import static com.tcs.edu.decorator.SeverityDecorator.mapToString;
import static com.tcs.edu.decorator.TimestampMessageDecorator.*;
import static com.tcs.edu.printer.ConsolePrinter.print;
/**
 * Преобразование декорированного сообщения, уровня важности и разделителя в строку
 *
 * @author  t.m.kharchenko
 * @see     #processMessage(Severity, String)  Метод для преобразования декорированного сообщения, уровня важности и разделителя в строку
 */
public class MessageService {
    /**
     * Метод преобразует декорированное сообщение, уровень важности и разделитель страницы в строку для вывода в консоль
     * и выводит полученную строку на печать.
     * Побочные эффекты пока отсутствуют.
     *
     * @param level     Перечислимый тип (переменная типа Severity) с уровнем важности сообщения
     * @param message   Строка (переменная типа String) с сообщением для декорирования
     * @see             MessageService Родительский класс
     */
    public static void processMessage (Severity level, String message) {
        final String TimestampDecoratedMessage = decorate(message);
        final String SeverityDecoratedString = mapToString(level);
        final String PagingDecoratedString = messageToPage(messageCount);
        print(String.format("%s %s %s", TimestampDecoratedMessage, SeverityDecoratedString, PagingDecoratedString));
    }
}
