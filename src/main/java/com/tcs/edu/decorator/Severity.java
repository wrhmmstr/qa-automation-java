package com.tcs.edu.decorator;
/**
 * Перечислимый тип, кодирующий уровень значимости выводимых сообщений
 */
public enum Severity {
    MINOR("()"), REGULAR("(!)"), MAJOR("(!!!)");

    private final String severity;

    Severity(String severity) {
        this.severity = severity;
    }

    public String getSeverity() {
        return severity;
    }
}
