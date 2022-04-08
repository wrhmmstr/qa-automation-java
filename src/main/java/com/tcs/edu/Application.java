package com.tcs.edu;

import com.tcs.edu.decorator.TimestampMessageDecorator;
import com.tcs.edu.printer.ConsolePrinter;
import com.tcs.edu.randomizer.ConsoleOutputRandomizer;

class Application {
    public static void main(String[] args) {
        for (int i = 0; i < ConsoleOutputRandomizer.randomize(2, 5); ++i){
            ConsolePrinter.print(TimestampMessageDecorator.decorate("Hello world!"));
        }
    }
}