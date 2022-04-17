package com.tcs.edu;

import com.tcs.edu.decorator.MessageService;
import com.tcs.edu.decorator.Severity;
import com.tcs.edu.randomizer.ConsoleOutputRandomizer;

class Application {
    public static void main(String[] args) {
        for (int i = 0; i < ConsoleOutputRandomizer.randomize(4, 8); ++i){
            MessageService.processMessage(Severity.MINOR,"Hello world!");
        }
    }
}