package com.company.service.util;

import org.apache.log4j.Logger;

import java.util.Random;

public class DigitCodeGenerator implements VerificationCodeGenerator {
    // ограничение, задает тип хранения кода (int)
    private static final int MAX_LENGTH = 9;
    // из соображений безопасности
    private static final int MIN_LENGTH = 3;
    private int length;

    private static final Logger LOGGER = Logger.getLogger(DigitCodeGenerator.class);

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public void setLength(int length) {
        if(MIN_LENGTH < 0 || length > MAX_LENGTH) {
            throw new ArrayIndexOutOfBoundsException();
        }
        this.length = length;
        LOGGER.info("set code length: " + length);
    }

    @Override
    public String generate() {
        int result = new Random().nextInt(calculateBound());
        return Integer.toString(result);
    }

    public int calculateBound() {
        int result = 9;
        for(int i = 1; i < length; i++) {
            result = result * 10 + 9;
        }
        return result;
    }
}
