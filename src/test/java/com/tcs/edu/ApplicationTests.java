package com.tcs.edu;

import com.tcs.edu.decorator.OrderDoublingProcessor;
import com.tcs.edu.decorator.PagingDecorator;
import com.tcs.edu.decorator.TimestampMessageDecorator;
import com.tcs.edu.domain.*;
import com.tcs.edu.printer.ConsolePrinter;
import com.tcs.edu.repository.HashMapMessageRepository;
import com.tcs.edu.repository.MessageRepository;
import com.tcs.edu.service.DecoratingMessageService;
import org.junit.Test;

import java.util.Collection;
import java.util.UUID;

public class ApplicationTests {

    Message message1 = new Message(Severity.MAJOR, "Hello world!");
    Message message2 = new Message(Severity.MAJOR, "Hello world!");
    Message message3 = new Message(Severity.REGULAR, "Ciao mondo!");
    Message message4 = new Message(Severity.MINOR, null);
    Message message5 = new Message(null, "¡Hola Mundo!");
    Message message6 = new Message(Severity.MAJOR, "Hello world!");
    Message message7 = new Message(Severity.MINOR, "");

    MessageService service = new DecoratingMessageService(
            new ConsolePrinter(),
            new HashMapMessageRepository(),
            new OrderDoublingProcessor(),
            new TimestampMessageDecorator(),
            new PagingDecorator()
    );

//    @Test
//    public void happyPathWithPrint() {
//        service.processMessages(message1, message2, message3, message4, message5, message6);
//        service.processMessages(MessageOrder.ASC, message1, message2, message3, message4, message5, message6);
//        service.processMessages(MessageOrder.ASC, Doubling.DOUBLES, message1, message2, message3, message4, message5, message6);
//        service.processMessages(MessageOrder.ASC, Doubling.DISTINCT, message1, message2, message3, message4, message5, message6);
//        service.processMessages(MessageOrder.DESC, Doubling.DISTINCT, message1, message2, message3, message4, message5, message6);
//
//        System.out.println(message1);
//        System.out.println(message1.equals(message2));
//        System.out.println(message1.equals(message3));
//        System.out.println(message1.hashCode()==message2.hashCode());
//        System.out.println(message1.hashCode()==message3.hashCode());
//        service.processMessage(message3);
//    }

    @Test
    public void happyPathFindByPrimaryKey() {

        System.out.println(service.findByPrimaryKey(service.processMessage(message4)));

    }

    @Test
    public void happyPathFindAll() {

        service.processMessages(MessageOrder.ASC, Doubling.DISTINCT, message1, message2, message3, message4, message5, message6);
        final Collection<DecoratedMessage> allMessages = service.findAll();
        for (DecoratedMessage current : allMessages) {
            System.out.println(current);
        }
    }

    @Test
    public void happyPathFindBySeverity() {

        service.processMessages(MessageOrder.ASC, Doubling.DOUBLES, message1, message2, message3, message4, message5, message6);
        final Collection<DecoratedMessage> allMessages = service.findBySeverity(Severity.MAJOR);
        for (DecoratedMessage current : allMessages) {
            System.out.println(current);
        }
    }

    @Test
    public void unhappyPathEmptyMessageWithPrint() {
        service.processMessages(MessageOrder.ASC, Doubling.DISTINCT, message1, message2, message3, message4, message5, message7);

    }

    @Test
    public void unhappyPathNullOrderWithPrint() {
        service.processMessages(null, Doubling.DISTINCT, message1, message2, message3, message4, message5, message6);
//        service.processMessages(MessageOrder.DESC, (Doubling) null, message1, message2, message3, message4, message5, message6);
//        service.processMessages(MessageOrder.DESC, Doubling.DISTINCT, message1, null, message3, message4, message5, message6);
    }
}
