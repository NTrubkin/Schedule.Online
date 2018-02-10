package com.company.util;

import java.lang.reflect.ParameterizedType;

/**
 * Простая обертка утилит рефлексии обобщенных классов
 */
public class GenericReflector {
    private GenericReflector() {
    }

    /**
     * Определяет класс параметра обобщенного класса
     *
     * @param generic
     * @return
     */
    public static Class getClassParameterType(Class generic) {
        return ((Class) ((ParameterizedType) generic.getGenericSuperclass()).getActualTypeArguments()[0]);
    }
}
