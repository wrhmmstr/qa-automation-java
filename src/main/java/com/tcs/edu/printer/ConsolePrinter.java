package com.tcs.edu.printer;

/**
 * Вывод информации в консоль
 *
 * @author  t.m.kharchenko
 * @see     #print(String) Метод для вывода информации в консоль
 */

public class ConsolePrinter {
    /**
     * Метод выводит переданную информацию в консоль и переходит на новую строку.
     * Побочные эффекты пока отсутствуют.
     *
     * @param message   Строка (переменная типа String) для вывода
     * @see             ConsolePrinter Родительский класс
     */
    public static void print(String message) {
        System.out.println(message);
    }
}
