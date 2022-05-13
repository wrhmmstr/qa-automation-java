package com.tcs.edu;

import com.tcs.edu.domain.Message;

public interface MessageDecorator {
    String decorate(Message message);

    String messageToPage(int messageCount);
}
