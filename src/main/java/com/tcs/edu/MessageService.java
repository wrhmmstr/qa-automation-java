package com.tcs.edu;

import com.tcs.edu.domain.*;

import java.util.Collection;
import java.util.UUID;

public interface MessageService {
    UUID processMessage(Message message) throws LogException;

    void processMessages(Message message, Message... messages) throws LogException;

    void processMessages(MessageOrder order, Message message, Message... messages) throws LogException;

    void processMessages(MessageOrder order, Doubling doubling, Message message, Message... messages) throws LogException;

    DecoratedMessage findByPrimaryKey (UUID key);

    Collection<DecoratedMessage> findAll();

    Collection<DecoratedMessage> findBySeverity(Severity by);
}
