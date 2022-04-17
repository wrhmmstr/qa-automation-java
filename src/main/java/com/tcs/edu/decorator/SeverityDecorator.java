package com.tcs.edu.decorator;

public class SeverityDecorator {
    /**
     * Метод преобразует перечислимый тип в строку
     * Побочные эффекты пока отсутствуют.
     *
     * @param severity  Перечислимый тип (переменная типа Severity) с уровнем важности сообщения
     * @return          Строка c уровнем важности сообщения
     * @see             SeverityDecorator Родительский класс
     */
    public static String mapToString (Severity severity) {
        String severityString = null;
        switch (severity) {
            case MINOR: {
                severityString = "()";
                break;
            }
            case REGULAR: {
                severityString = "(!)";
                break;
            }
            case MAJOR: {
                severityString = "(!!!)";
                break;
            }
        }
        return severityString;
    }
}
