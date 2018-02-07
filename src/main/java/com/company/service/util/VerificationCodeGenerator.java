package com.company.service.util;

public interface VerificationCodeGenerator {

    public int getLength();
    void setLength(int length);
    String generate();
}
