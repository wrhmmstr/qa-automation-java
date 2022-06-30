package com.tcs.edu;

import com.tcs.edu.domain.Message;
import com.tcs.edu.domain.MessageOrder;

public interface MessageOrderProcessor {

    Message[] process(MessageOrder order, Message... messages);

}
