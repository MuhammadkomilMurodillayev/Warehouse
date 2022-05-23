package com.example.warehouse.service;

import com.example.warehouse.mapper.BaseMapper;
import com.example.warehouse.repository.BaseRepository;
import com.example.warehouse.validation.BaseValidation;

public abstract class AbstractService<
        R extends BaseRepository,
        M extends BaseMapper,
        V extends BaseValidation> {

    protected final R repository;
    protected final M mapper;
    protected final V validation;

    protected AbstractService(R repository, M mapper, V validation) {
        this.repository = repository;
        this.mapper = mapper;
        this.validation = validation;
    }
}
