package com.tcs.edu;

import com.tcs.edu.decorator.Doubling;
import com.tcs.edu.decorator.MessageOrder;
import com.tcs.edu.domain.Message;

public interface MessageService {
    void processMessage(Message message);

    void processMessages(Message message, Message... messages);

    void processMessages(MessageOrder order, Message message, Message... messages);

    void processMessages(MessageOrder order, Doubling doubling, Message message, Message... messages);
}
