package com.tcs.edu.decorator;

import com.tcs.edu.MessageDecorator;

/**
 * Добавление разделителя страницы
 *
 * @author  t.m.kharchenko
 * @see     #decorate(String) Метод для добавления порядкового номера страницы
 *                                 и разделителя страницы после строки кратной PAGE_SIZE
 */
public class PagingDecorator implements MessageDecorator {
    /**
     * Переменная с порядковым номером строки
     */
    public static int messageCount = 0;

    /**
     * Переменная с количеством строк на одной странице
     */
    public int pageSize = 0;

    public PagingDecorator(int pageSize) {
        this.pageSize = pageSize;
    }
    public PagingDecorator() {
        this(2);
    }

    /**
     * Метод добавляет разделитель страницы после строки кратной PAGE_SIZE
     * Побочные эффекты пока отсутствуют.
     *
     * @param message       Строка (переменная типа String) c сообщением для декорирования
     * @return              Строка c разделителем страницы
     * @see                 PagingDecorator Родительский класс
     */
    public String decorate(String message){
        ++messageCount;
        final String decoratedMessage;
        if (messageCount % pageSize == 0) {
            decoratedMessage = String.format("%d %s %n---", messageCount, message);
        } else {
            decoratedMessage = String.format("%d %s", messageCount, message);
        }
        return decoratedMessage;
    }
}
