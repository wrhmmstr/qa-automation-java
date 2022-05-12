package com.tcs.edu.domain;

import com.tcs.edu.decorator.Severity;

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
}
