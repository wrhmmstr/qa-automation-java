package com.tcs.edu;

import com.tcs.edu.decorator.Doubling;
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

        processMessages(null, "Hello world! 1", "Ciao mondo!", "¡Hola Mundo!");
        processMessages(Severity.REGULAR, (MessageOrder) null, "Hello world! 1", null, "¡Hola Mundo! 3", "Hallo Welt! 4");
        processMessages(Severity.REGULAR, MessageOrder.DESC, "Hello world! 1", null, "¡Hola Mundo! 3", "Hallo Welt! 4");
        processMessages(Severity.REGULAR, MessageOrder.ASC, Doubling.DISTINCT, "Hello world!", null, "Hello world!", "Ciao mondo!", "¡Hola Mundo!", "Hallo Welt!");
        processMessages(Severity.REGULAR, null, Doubling.DISTINCT, "Hello world!", null, "Hello world!", "Ciao mondo!", "¡Hola Mundo!", "Hallo Welt!");
        processMessages(Severity.MAJOR, MessageOrder.DESC, Doubling.DISTINCT, "Hello world!", "Hello world!", "Ciao mondo!", "¡Hola Mundo!", "Hallo Welt!", "Hei Verden!", "Hei maailma!");


    }
}