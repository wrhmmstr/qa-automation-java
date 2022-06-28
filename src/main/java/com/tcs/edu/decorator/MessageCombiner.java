package com.tcs.edu.decorator;

import com.tcs.edu.domain.Message;
import com.tcs.edu.service.ValidatingService;

public class MessageCombiner extends ValidatingService {
    public Message[] combineMessages(Message message, Message... messages) {
        super.isArgsValid(message);
        Message[] combinedMessages = new Message[messages.length+1];
        int currentMessage = 0;
        combinedMessages[currentMessage] = message;
        for (currentMessage = 1; currentMessage < combinedMessages.length; currentMessage++) {
            combinedMessages[currentMessage] = messages[currentMessage-1];
        }
        return combinedMessages;
    }
}
