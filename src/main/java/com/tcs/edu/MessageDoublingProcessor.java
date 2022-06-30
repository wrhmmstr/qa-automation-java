package com.tcs.edu;

import com.tcs.edu.domain.Doubling;
import com.tcs.edu.domain.Message;
import com.tcs.edu.domain.MessageOrder;

public interface MessageDoublingProcessor {

    Message[] process(MessageOrder order, Doubling doubling, Message... messages);

}
