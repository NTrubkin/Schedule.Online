package com.company.util;

import org.apache.commons.lang3.StringUtils;

public class CommonValidator {
    private static final int MIN_NAME_LENGTH = 1;
    private static final int MAX_NAME_LENGTH = 40;
    private static final int MIN_TEXT_LENGTH = 1;
    private static final int MAX_TEXT_LENGTH = 1000;


    private CommonValidator() {
    }

    public static boolean isNameValid(String name) {
        return (StringUtils.isNotEmpty(name)) && (name.length() >= MIN_NAME_LENGTH) && (name.length() <= MAX_NAME_LENGTH);
    }

    public static boolean isTextValid(String text) {
        return (StringUtils.isNotEmpty(text)) && (text.length() >= MIN_TEXT_LENGTH) && (text.length() <= MAX_TEXT_LENGTH);
    }
}
