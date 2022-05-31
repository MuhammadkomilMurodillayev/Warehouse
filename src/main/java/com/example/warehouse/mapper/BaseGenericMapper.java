package com.example.warehouse.mapper;

import com.example.warehouse.dto.BaseDto;
import com.example.warehouse.dto.BaseGenericDto;
import com.example.warehouse.entity.BaseEntity;

import java.util.List;

public interface BaseGenericMapper<
        E extends BaseEntity,
        D extends BaseGenericDto,
        CD extends BaseDto,
        UD extends BaseGenericDto> extends BaseMapper {

    E fromCreateDto(CD dto);

    E fromUpdateDto(E e, UD dto);

    D toDto(E entity);

    List<D> toDto(List<E> entities);

}
