package com.tcs.edu.randomizer;

import com.tcs.edu.domain.Severity;
import java.util.Random;
/**
 * Вычисление случайного случайного порядкового номера enum Severity
 *
 * @author  t.m.kharchenko
 * @see     #randomize() Метод для вычисления случайного порядкового номера enum Severity
 */
public class EnumRandomizer {
    public static Severity randomize() {
        Random random = new Random();
        return Severity.values()[random.nextInt(Severity.values().length)];
    }
}
