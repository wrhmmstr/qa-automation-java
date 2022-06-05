package com.tcs.edu.domain;

import java.util.Objects;

public class Message {
    private final Severity level;
    private final String message;

    public Message(Severity level, String message) {
        this.level = level;
        this.message = message;
    }

    public Severity getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "level=" + level +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return getLevel() == message1.getLevel() && Objects.equals(getMessage(), message1.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLevel(), getMessage());
    }
}
