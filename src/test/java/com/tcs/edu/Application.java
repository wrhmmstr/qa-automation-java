package com.tcs.edu;

import com.tcs.edu.decorator.MessageService;
import com.tcs.edu.decorator.Severity;
import com.tcs.edu.randomizer.ConsoleOutputRandomizer;
import com.tcs.edu.randomizer.EnumRandomizer;

class Application {
    public static void main(String[] args) {
        /*for (int i = 0; i < ConsoleOutputRandomizer.randomize(4, 8); ++i){
            MessageService.processMessage(EnumRandomizer.randomize(), "Hello world!", "Ciao mondo!", "¡Hola Mundo!");
        }*/
        MessageService.processMessage(Severity.MINOR, "Hello world!", "Ciao mondo!", "¡Hola Mundo!");
        MessageService.processMessage(Severity.REGULAR, "Hello world!", "Ciao mondo!", "¡Hola Mundo!");
        MessageService.processMessage(Severity.REGULAR, "Hello world!", "Ciao mondo!", "¡Hola Mundo!");
        MessageService.processMessage(Severity.MAJOR, "Hello world!", "Ciao mondo!", "¡Hola Mundo!");
        MessageService.processMessage(Severity.MINOR, "Hello world!", "Ciao mondo!", "¡Hola Mundo!");
        MessageService.processMessage(Severity.MAJOR, "Hello world!", "Ciao mondo!", "¡Hola Mundo!");
    }
}