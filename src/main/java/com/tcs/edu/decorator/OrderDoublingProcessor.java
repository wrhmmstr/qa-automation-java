package com.tcs.edu.decorator;

import com.tcs.edu.MessageProcessor;
import com.tcs.edu.domain.Message;

public class OrderDoublingProcessor implements MessageProcessor {

    @Override
    public Message[] process (MessageOrder order, Message... messages) {
        Message[] processedMessages = new Message[0];
        switch (order) {
            case ASC: {
                processedMessages = messages;
                break;
            }
            case DESC: {
                processedMessages = new Message[messages.length];
                int currentDescMessage = 0;
                for (int currentMessage = messages.length - 1; currentMessage >= 0; currentMessage--) {
                    processedMessages[currentDescMessage] = messages[currentMessage];
                    currentDescMessage++;
                }
                break;
            }
        }
        return processedMessages;
    }

    @Override
    public Message[] process(MessageOrder order, Doubling doubling, Message... messages) {
        return new Message[0];
    }
}
