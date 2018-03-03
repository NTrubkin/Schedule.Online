package com.company.util;

import org.apache.commons.lang3.StringUtils;

public class CommonValidator {
    public static final int MIN_NAME_LENGTH = 1;
    public static final int MAX_NAME_LENGTH = 40;
    public static final int MIN_TEXT_LENGTH = 1;
    public static final int MAX_TEXT_LENGTH = 1000;
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 40;


    private CommonValidator() {
    }

    public static boolean isNameValid(String name) {
        return (StringUtils.isNotEmpty(name)) && (name.length() >= MIN_NAME_LENGTH) && (name.length() <= MAX_NAME_LENGTH);
    }

    public static boolean isTextValid(String text) {
        return (StringUtils.isNotEmpty(text)) && (text.length() >= MIN_TEXT_LENGTH) && (text.length() <= MAX_TEXT_LENGTH);
    }

    public static boolean isPasswordValid(String password) {
        return (StringUtils.isNotEmpty(password)) && (password.length() >= MIN_PASSWORD_LENGTH) && (password.length() <= MAX_PASSWORD_LENGTH);
    }
}
