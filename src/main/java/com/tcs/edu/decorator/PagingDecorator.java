//TODO удалить неиспользуемый класс

package com.tcs.edu.decorator;

import com.tcs.edu.MessageDecorator;

/**
 * Добавление разделителя страницы
 *
 * @author  t.m.kharchenko
 * @see     #messageToPage(int) Метод для добавления разделителя страницы после строки кратной PAGE_SIZE
 */
public class PagingDecorator {
    /**
     * Переменная с количеством строк на одной странице
     */
    private static final int PAGE_SIZE = 2;
    /**
     * Метод добавляет разделитель страницы после строки кратной PAGE_SIZE
     * Побочные эффекты пока отсутствуют.
     *
     * @param messageCount  Число (переменная типа int) c порядковым номером выводимой строки
     * @return              Строка c разделителем страницы
     * @see                 PagingDecorator Родительский класс
     */
    public static String messageToPage(int messageCount){

        if (messageCount % PAGE_SIZE == 0) {
            final var decoratedPage = String.format("%n---");
            return decoratedPage;
        } else {
            final var decoratedPage = String.format("");
            return decoratedPage;
        }
    }
}
