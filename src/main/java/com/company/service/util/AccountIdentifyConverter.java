package com.company.service.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

//
//
// В org.springframework.security.core.Authentication идентификатор это "имя" через метод getName()
// Этот класс конвертирует идентификаторы обоих сервисов

/**
 * Решает проблему идентификации аккаунта
 * В сущности Account и базе данных идентификатор это поле id
 * В org.springframework.security.core.Authentication идентификатор это "name" через метод getName()
 * Этот класс конвертирует идентификаторы обоих сервисов
 * <p>
 * В данной реализации id - это целое число любой длины, "name" - удовлетворяет regex NAME_REGEX
 */
@Service
public class AccountIdentifyConverter {
    private static final String NAME_REGEX = "id\\d+";
    private static Pattern namePattern = Pattern.compile(NAME_REGEX);

    public String IdToName(int id) {
        return "id" + id;
    }

    public int NameToId(String name) {
        if (StringUtils.isEmpty(name) || !namePattern.matcher(name).matches()) {
            throw new IllegalArgumentException(String.format("Name %s does not match pattern %s. " +
                    "Probably, naming convention was changed and this method became deprecated", name, NAME_REGEX));
        }
        return new Integer(name.substring(2));
    }
}
