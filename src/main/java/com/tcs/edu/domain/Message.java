package com.tcs.edu.domain;

import java.util.Objects;
import java.util.UUID;

public class Message {
    Severity level;
    String message;
//    public UUID id;

    public Message(Severity level, String message) {
        this.level = level;
        this.message = message;
//        this.id = id;
    }

    public Severity getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

//    public UUID getId() {
//        return id;
//    }
//
//    public void setId(UUID id) {
//        this.id=id;
//    }

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
