package com.company.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginValidator {
    // todo изучить spring validator

    private static final int MAX_EMAIL_LENGTH = 240;
    private static final String EMAIL_REGEX =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static Pattern emailPattern = Pattern.compile(EMAIL_REGEX);

    private static final String PHONE_REGEX = "[0-9]{10}";
    private static Pattern phonePattern = Pattern.compile(PHONE_REGEX);

    public static boolean isEmailValid(String email) {
        Matcher matcher = emailPattern.matcher(email);
        return (StringUtils.isNotEmpty(email)) && (email.length() <= MAX_EMAIL_LENGTH) && (checkRegex(emailPattern, email));
    }

    public static boolean isPhoneNumberValid(String phoneNumber) {

        return (StringUtils.isNotEmpty(phoneNumber)) && (checkRegex(phonePattern, phoneNumber));
    }

    private static boolean checkRegex(Pattern pattern, String text) {
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }
}
