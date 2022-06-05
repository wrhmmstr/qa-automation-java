package com.tcs.edu.decorator;

import com.tcs.edu.MessageProcessor;
import com.tcs.edu.domain.Message;

import java.util.Objects;

public class OrderDoublingProcessor implements MessageProcessor {

    @Override
    public Message[] process (MessageOrder order, Message... messages) {
        Message[] processedMessages = new Message[messages.length];
        switch (order) {
            case ASC: {
                processedMessages = messages;
                break;
            }
            case DESC: {
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
        Message[] processedMessages = new Message[messages.length];
        switch (doubling) {
            case DOUBLES: {
                processedMessages = process (order, messages);
                break;
            }
            case DISTINCT: {
                int currentMessageIndex = 0;
                int distinctWrittenMessageIndex = 0;
                Message[] orderedMessages = process(order, messages);
                processedMessages[distinctWrittenMessageIndex] = orderedMessages[currentMessageIndex];
                for (currentMessageIndex = 0; currentMessageIndex < orderedMessages.length; currentMessageIndex++) {
                    Message currentMessage = orderedMessages[currentMessageIndex];
                    boolean isPrinted = false;
                    for (int distinctMessageIndex = 0; distinctMessageIndex <= distinctWrittenMessageIndex; distinctMessageIndex++) {
                        if (Objects.equals(currentMessage.getMessage(), processedMessages[distinctMessageIndex].getMessage())) {
                            isPrinted = true;
                            break;
                        }
                    }
                    if (!isPrinted) {
                        ++distinctWrittenMessageIndex;
                        processedMessages[distinctWrittenMessageIndex] = currentMessage;
                    }
                }
                break;
            }
        }
        return processedMessages;
    }
}
