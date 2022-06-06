package com.tcs.edu;

import com.tcs.edu.decorator.*;
import com.tcs.edu.domain.Doubling;
import com.tcs.edu.domain.Message;
import com.tcs.edu.domain.MessageOrder;
import com.tcs.edu.domain.Severity;
import com.tcs.edu.printer.ConsolePrinter;
import com.tcs.edu.service.DecoratingMessageService;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

class Application {

    public static void main(String[] args) {
        /*for (int i = 0; i < ConsoleOutputRandomizer.randomize(4, 8); ++i){
            processMessages(EnumRandomizer.randomize(), "Hello world!", "Ciao mondo!", "¡Hola Mundo!");
        }*/

        Result result = JUnitCore.runClasses(ApplicationTests.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }
}

