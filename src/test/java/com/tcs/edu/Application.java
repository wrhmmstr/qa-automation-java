package com.tcs.edu;

import com.tcs.edu.decorator.MessageOrder;
import com.tcs.edu.decorator.Severity;
import com.tcs.edu.randomizer.ConsoleOutputRandomizer;
import com.tcs.edu.randomizer.EnumRandomizer;
import static com.tcs.edu.decorator.MessageService.processMessages;

class Application {
    public static void main(String[] args) {
        /*for (int i = 0; i < ConsoleOutputRandomizer.randomize(4, 8); ++i){
            processMessages(EnumRandomizer.randomize(), "Hello world!", "Ciao mondo!", "¡Hola Mundo!");
        }*/

        //для проверки домашнего задания 7-1:
        /*processMessages(Severity.MINOR, null, "Ciao mondo!", "¡Hola Mundo!");
        processMessages(Severity.REGULAR, "Hello world!", null, "¡Hola Mundo!");
        processMessages(Severity.REGULAR, "Hello world!", "Ciao mondo!", null);
        processMessages(null, "Hello world!", "Ciao mondo!", "¡Hola Mundo!");
        processMessages(Severity.MINOR, null, null, "¡Hola Mundo!");
        processMessages(null, null, null, null);*/

        //для проверки домашнего задания 7-1 и 7-2:
        processMessages(Severity.MINOR, MessageOrder.DESC, null, "Ciao mondo! 2", "¡Hola Mundo! 3", "Hallo Welt! 4");
        processMessages(Severity.REGULAR, MessageOrder.DESC, "Hello world! 1", null, "¡Hola Mundo! 3", "Hallo Welt! 4");
        processMessages(Severity.REGULAR, MessageOrder.DESC, "Hello world! 1", "Ciao mondo! 2", null, "Hallo Welt! 4");
        processMessages(null, MessageOrder.DESC, "Hello world! 1", "Ciao mondo! 2", "¡Hola Mundo! 3", "Hallo Welt! 4");
        processMessages(Severity.MINOR, MessageOrder.DESC, null, null, "¡Hola Mundo! 3", "Hallo Welt! 4");
        processMessages(null, MessageOrder.DESC, null, null, null, "Hallo Welt! 4");


    }
}