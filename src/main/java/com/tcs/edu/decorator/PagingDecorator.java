package com.tcs.edu.decorator;
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
    public static int PAGE_SIZE = 2; //TODO передавать значение размера страницы из класса Application
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
            final var decoratedMessage = String.format("%n---");
            return decoratedMessage;
        } else {
            final var decoratedMessage = String.format("");
            return decoratedMessage;
        }
    }
}
