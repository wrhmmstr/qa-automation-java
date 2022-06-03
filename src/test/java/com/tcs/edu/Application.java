package com.tcs.edu;

import com.tcs.edu.decorator.*;
import com.tcs.edu.domain.Message;
import com.tcs.edu.domain.Severity;
import com.tcs.edu.printer.ConsolePrinter;


class Application {
    public static void main(String[] args) {
        /*for (int i = 0; i < ConsoleOutputRandomizer.randomize(4, 8); ++i){
            processMessages(EnumRandomizer.randomize(), "Hello world!", "Ciao mondo!", "¡Hola Mundo!");
        }*/

        Message message1 = new Message(Severity.MAJOR, "Hello world!");
        Message message2 = new Message(Severity.MAJOR, "Hello world!");
        Message message3 = new Message(Severity.REGULAR, "Ciao mondo!");
        Message message4 = new Message(Severity.MINOR, null);
        Message message5 = new Message(null, "¡Hola Mundo!");
        Message message6 = new Message(Severity.MAJOR, "Hello world!");

        MessageService service = new DecoratingMessageService(
                new ConsolePrinter(),
                new OrderDoublingProcessor(),
                new TimestampMessageDecorator(),
                new PagingDecorator()
        );

//        service.processMessages(message1, message2, message3, message4, message5, message6);
        service.processMessages(MessageOrder.DESC, message1, message2, message3, message4, message5, message6);
//        service.processMessages(MessageOrder.ASC, Doubling.DISTINCT, message1, message2, message3, message4, message5, message6);
//        service.processMessages(MessageOrder.DESC, Doubling.DISTINCT, message1, message2, message3, message4, message5, message6);
    }
}