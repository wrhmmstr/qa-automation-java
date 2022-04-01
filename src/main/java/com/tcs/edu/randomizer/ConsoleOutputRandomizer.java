package com.tcs.edu.randomizer;

/**
 * Вычисление случайного множителя для множественного вывода строк в консоль. Требуется для проверки домашнего задания 4-2.
 *
 * @author  t.m.kharchenko
 * @see     #randomize(int, int) Метод для вычисления случайного множителя
 */
public class ConsoleOutputRandomizer {
    /**
     * Метод вычисляет случайное число между заданными минимальным и максимальным
     *
     * @param min   Целое число (переменная типа int), минимальное значение (включительно) для выбора случайного числа
     * @param max   Целое число (переменная типа int), максимальное значение (исключительно) для выбора случайного числа
     * @see         ConsoleOutputRandomizer Родительский класс
     */
    public static int randomize(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
