package com.example.warehouse.service.container;

import com.example.warehouse.criteria.container.ContainerCriteria;
import com.example.warehouse.dto.container.ContainerCreateDto;
import com.example.warehouse.dto.container.ContainerDto;
import com.example.warehouse.dto.container.ContainerUpdateDto;
import com.example.warehouse.mapper.container.ContainerMapper;
import com.example.warehouse.repository.container.ContainerRepository;
import com.example.warehouse.service.AbstractService;
import com.example.warehouse.service.BaseCrudService;
import com.example.warehouse.validation.ContainerValidation;

import java.util.List;

public class ContainerService
        extends AbstractService<
        ContainerRepository,
        ContainerMapper,
        ContainerValidation>
        implements BaseCrudService<
        ContainerDto,
        ContainerUpdateDto,
        ContainerCreateDto,
        ContainerCriteria,
        String>
        {

    @Override
    public ContainerDto get(String id) {
        return null;
    }

    @Override
    public List<ContainerDto> getAll(ContainerCriteria criteria) {
        return null;
    }

    @Override
    public String create(ContainerCreateDto dto) {
        return null;
    }

    @Override
    public void update(ContainerUpdateDto dto) {

    }

    @Override
    public void delete(String id) {

    }
}
