package com.tcs.edu;

import com.tcs.edu.decorator.Doubling;
import com.tcs.edu.decorator.MessageOrder;
import com.tcs.edu.domain.Message;

public interface MessageProcessor {

    Message[] process(MessageOrder order, Message... messages);

    Message[] process(MessageOrder order, Doubling doubling, Message... messages);

}
