package com.tcs.edu.service;

import com.tcs.edu.domain.Doubling;
import com.tcs.edu.domain.Message;
import com.tcs.edu.domain.MessageOrder;
import com.tcs.edu.domain.Severity;

public abstract class ValidatingService {
    public boolean isArgsValid (Message message) {
        if (message == null) return false;
        if (message.getMessage() == null) return false;
        if (message.getMessage().isEmpty()) throw new IllegalArgumentException("Message message.getMessage() value is: " + message.getMessage() + ".");
        return true;
    }

    public boolean isArgsValid (String message) {
        if (message == null) throw new IllegalArgumentException("String message value is: " + message + ".");
        return true;
    }

    public boolean isArgsValid (Severity level) {
        if (level == null) return false;
        return true;
    }

    public boolean isArgsValid (MessageOrder order) {
        if (order == null) throw new IllegalArgumentException("MessageOrder order value is: " + order + ".");
        return true;
    }

    public boolean isArgsValid (MessageOrder order, Doubling doubling) {
        if (order == null) throw new IllegalArgumentException("MessageOrder order value is: " + order + ".");
        if (doubling == null) throw new IllegalArgumentException("Doubling doubling value is: " + doubling + ".");
        return true;
    }
}
