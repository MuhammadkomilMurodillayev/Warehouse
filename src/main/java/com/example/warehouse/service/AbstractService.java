package com.example.warehouse.service;

import com.example.warehouse.mapper.BaseMapper;
import com.example.warehouse.repository.BaseRepository;
import com.example.warehouse.validation.BaseValidation;
import lombok.Getter;

public abstract class AbstractService<
        R extends BaseRepository,
        M extends BaseMapper,
        V extends BaseValidation> {

    protected R repository;
    protected M mapper;
    protected V validation;
}
