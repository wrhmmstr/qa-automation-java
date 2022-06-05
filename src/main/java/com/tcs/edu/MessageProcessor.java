package com.tcs.edu;

import com.tcs.edu.domain.Doubling;
import com.tcs.edu.domain.MessageOrder;
import com.tcs.edu.domain.Message;

public interface MessageProcessor {

    Message[] process(MessageOrder order, Message... messages);

    Message[] process(MessageOrder order, Doubling doubling, Message... messages);

}
