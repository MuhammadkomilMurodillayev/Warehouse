package com.example.warehouse.service.warehouse;

import com.example.warehouse.criteria.warehouse.WarehouseCriteria;
import com.example.warehouse.dto.warehouse.WarehouseCreateDto;
import com.example.warehouse.dto.warehouse.WarehouseDto;
import com.example.warehouse.dto.warehouse.WarehouseUpdateDto;
import com.example.warehouse.mapper.warehouse.WarehouseMapper;
import com.example.warehouse.repository.warehouse.WarehouseRepository;
import com.example.warehouse.service.AbstractService;
import com.example.warehouse.service.BaseCrudService;
import com.example.warehouse.validation.warehouse.WarehouseValidation;

import java.util.List;

public class WarehouseService
        extends AbstractService<
        WarehouseRepository,
        WarehouseMapper,
        WarehouseValidation>
implements BaseCrudService<
        WarehouseDto,
        WarehouseUpdateDto,
        WarehouseCreateDto,
        WarehouseCriteria,
        String> {

    @Override
    public WarehouseDto get(String id) {
        return null;
    }

    @Override
    public List<WarehouseDto> getAll(WarehouseCriteria criteria) {
        return null;
    }

    @Override
    public String create(WarehouseCreateDto dto) {
        return null;
    }

    @Override
    public void update(WarehouseUpdateDto dto) {

    }

    @Override
    public void delete(String id) {

    }
}
