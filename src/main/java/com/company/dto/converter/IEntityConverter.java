package com.company.dto.converter;

import java.util.List;

public interface IEntityConverter<T1, T2> {
    T2 convert(T1 entity);

    List<T2> convert(List<T1> entities);

    T1 restore(T2 dto);

    List<T1> restore(List<T2> dtos);
}
