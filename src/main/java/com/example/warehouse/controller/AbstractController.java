package com.example.warehouse.controller;

import com.example.warehouse.criteria.BaseCriteria;
import com.example.warehouse.dto.BaseDto;
import com.example.warehouse.dto.BaseGenericDto;
import com.example.warehouse.dto.data.DataDto;
import com.example.warehouse.service.BaseService;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractController<
        S extends BaseService,
        D extends BaseGenericDto,
        CD extends BaseDto,
        UD extends BaseGenericDto,
        K extends Serializable,
        C extends BaseCriteria> {

    protected S service;
    protected final static String API = "/api";
    protected final static String VERSION = "/v1";
    public final static String PATH = API + VERSION;

    public AbstractController(S service) {
        this.service = service;
    }

    protected abstract ResponseEntity<DataDto<K>> create(CD dto);

    protected abstract ResponseEntity<DataDto<String>> delete(K id);

    protected abstract ResponseEntity<DataDto<String>> update(UD dto,K id);

    protected abstract ResponseEntity<DataDto<D>> get(K id);

    protected abstract ResponseEntity<DataDto<List<D>>> getAll(C criteria);


}
