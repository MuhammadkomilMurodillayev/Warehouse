package com.example.warehouse.service;

import com.example.warehouse.criteria.BaseCriteria;
import com.example.warehouse.dto.BaseDto;
import com.example.warehouse.dto.BaseGenericDto;

import java.io.Serializable;
import java.util.List;

public interface BaseCrudService<
        D extends BaseGenericDto,
        UD extends BaseGenericDto,
        CD extends BaseDto,
        C extends BaseCriteria,
        K extends Serializable> extends BaseService{

    D get(K id);

    List<D> getAll(C criteria);

    String create(CD dto);

    void update(UD dto);

    void delete(K id);
}
