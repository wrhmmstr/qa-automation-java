package com.tcs.edu.printer;

import com.tcs.edu.Printer;
import com.tcs.edu.service.ValidatingService;

/**
 * Вывод информации в консоль
 *
 * @author  t.m.kharchenko
 * @see     #print(String) Метод для вывода информации в консоль
 */

public class ConsolePrinter extends ValidatingService implements Printer {
    /**
     * Метод выводит в консоль порядковый номер переданной строки, содержимое строки, и переходит на новую строку.
     *
     * @param message   Строка (переменная типа String) для вывода
     * @see             ConsolePrinter Родительский класс
     */
    @Override
    public void print(String message) {
        super.isArgsValid(message);
        System.out.println(message);
    }
}
