package com.tcs.edu.printer;

//import com.tcs.edu.decorator.TimestampMessageDecorator;

/**
 * Вывод информации в консоль
 *
 * @author  t.m.kharchenko
 * @see     #print(String) Метод для вывода информации в консоль
 */

public class ConsolePrinter {
    /**
     * Метод выводит в консоль порядковый номер переданной строки, содержимое строки, и переходит на новую строку.
     * Побочный эффект изменение глобальной переменной messageCount / Side effect on global messageCount
     *
     * @param message   Строка (переменная типа String) для вывода
     * @see             ConsolePrinter Родительский класс
     */
    public static void print(String message) {
        //System.out.println(++TimestampMessageDecorator.messageCount + " " + message);
        System.out.println(message);
    }
}
