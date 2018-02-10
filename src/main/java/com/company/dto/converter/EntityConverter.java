package com.company.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

// todo подумать об автоматизации, см. modelmapper

public abstract class EntityConverter<T1, T2> implements IEntityConverter<T1, T2> {
    protected static final String STRING_STUB = "";

    @Override
    public List<T2> convert(List<T1> entities) {
        return entities
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<T1> restore(List<T2> dtos) {
        return dtos
                .stream()
                .map(this::restore)
                .collect(Collectors.toList());
    }
}
