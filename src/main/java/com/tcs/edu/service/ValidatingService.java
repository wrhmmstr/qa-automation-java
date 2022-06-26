package com.tcs.edu.service;

import com.tcs.edu.domain.*;

public abstract class ValidatingService {
    public void isArgsValid (Message message) {
        if (message == null) throw new IllegalArgumentException("Message value is" + "null" + ".");
        if (message.getMessage() == null) throw new IllegalArgumentException("Message message.getMessage() value is: " + message.getMessage() + ".");
        if (message.getMessage().isEmpty()) throw new IllegalArgumentException("Message message.getMessage() value is: " + message.getMessage() + ".");
        if (message.getLevel() == null) throw new IllegalArgumentException("Message message.getLevel() value is: " + message.getLevel() + ".");
    }

    public void isArgsValid (String message) {
        if (message == null) throw new IllegalArgumentException("String message value is: " + message + ".");
    }

//    public boolean isArgsValid (Severity level) {
//        if (level == null) throw new IllegalArgumentException("Message message.getLevel() value is: " + level + ".");;
//        return true;
//    }

    public void isArgsValid (MessageOrder order) {
        if (order == null) throw new IllegalArgumentException("MessageOrder order value is: " + order + ".");
    }

    public void isArgsValid (MessageOrder order, Doubling doubling) {
        if (order == null) throw new IllegalArgumentException("MessageOrder order value is: " + order + ".");
        if (doubling == null) throw new IllegalArgumentException("Doubling doubling value is: " + doubling + ".");
    }

//    public void isArgsValid (DecoratedMessage message) {
//        if (message == null) throw new IllegalArgumentException("DecoratedMessage message value is" + message.getMessage() + ".");
//        if (message.getMessage() == null) throw new IllegalArgumentException("DecoratedMessage message.getMessage() value is: " + message.getMessage() + ".");
//        if (message.getMessage().isEmpty()) throw new IllegalArgumentException("DecoratedMessage message.getMessage() value is: " + message.getMessage() + ".");
//        if (message.getLevel() == null) throw new IllegalArgumentException("DecoratedMessage message.getLevel() value is: " + message.getLevel() + ".");
//    }
}
