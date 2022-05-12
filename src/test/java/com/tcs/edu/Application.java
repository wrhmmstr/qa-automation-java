package com.tcs.edu;

import com.tcs.edu.decorator.DecoratingMessageService;
import com.tcs.edu.decorator.Doubling;
import com.tcs.edu.decorator.MessageOrder;
import com.tcs.edu.decorator.Severity;
import com.tcs.edu.domain.Message;


class Application {
    public static void main(String[] args) {
        /*for (int i = 0; i < ConsoleOutputRandomizer.randomize(4, 8); ++i){
            processMessages(EnumRandomizer.randomize(), "Hello world!", "Ciao mondo!", "¡Hola Mundo!");
        }*/

//        processMessages(null, "Hello world! 1", "Ciao mondo!", "¡Hola Mundo!");
//        processMessages(Severity.REGULAR, (MessageOrder) null, "Hello world! 1", null, "¡Hola Mundo! 3", "Hallo Welt! 4");
//        processMessages(Severity.REGULAR, MessageOrder.DESC, "Hello world! 1", null, "¡Hola Mundo! 3", "Hallo Welt! 4");
//        processMessages(Severity.REGULAR, MessageOrder.ASC, Doubling.DISTINCT, "Hello world!", null, "Hello world!", "Ciao mondo!", "¡Hola Mundo!", "Hallo Welt!");
//        processMessages(Severity.REGULAR, null, Doubling.DISTINCT, "Hello world!", null, "Hello world!", "Ciao mondo!", "¡Hola Mundo!", "Hallo Welt!");
//        processMessages(Severity.MAJOR, MessageOrder.DESC, Doubling.DISTINCT, "Hello world!", "Hello world!", "Ciao mondo!", "¡Hola Mundo!", "Hallo Welt!", "Hei Verden!", "Hei maailma!");

        Message message1 = new Message(Severity.MAJOR, "Hello world!");
        Message message2 = new Message(Severity.MAJOR, "Hello world!");
        Message message3 = new Message(Severity.REGULAR, "Ciao mondo!");
        Message message4 = new Message(Severity.MINOR, null);
        Message message5 = new Message(null, "¡Hola Mundo!");
        Message message6 = new Message(Severity.MAJOR, "Hello world!");

        MessageService service = new DecoratingMessageService();

//        processMessages(message1, message2, message3, message4, message5, message6);
//        processMessages(MessageOrder.ASC, message1, message2, message3, message4, message5, message6);
        service.processMessages(MessageOrder.ASC, Doubling.DISTINCT, message1, message2, message3, message4, message5, message6);
        service.processMessages(MessageOrder.DESC, Doubling.DISTINCT, message1, message2, message3, message4, message5, message6);
    }
}