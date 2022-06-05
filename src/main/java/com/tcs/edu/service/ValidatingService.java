package com.tcs.edu.service;

import com.tcs.edu.domain.Doubling;
import com.tcs.edu.domain.Message;
import com.tcs.edu.domain.MessageOrder;
import com.tcs.edu.domain.Severity;

public abstract class ValidatingService {
    public boolean isArgsValid (Message message) {
        if (message == null) return false;
        if (message.getMessage() == null) return false;
        if (message.getMessage().isEmpty()) return false;
        return true;
    }

    public boolean isArgsValid (Severity level) {
        if (level == null) return false;
        return true;
    }

    public boolean isArgsValid (MessageOrder order) {
        if (order == null) return false;
        return true;
    }

    public boolean isArgsValid (MessageOrder order, Doubling doubling) {
        if (order == null) return false;
        if (doubling == null) return false;
        return true;
    }
}
