package com.example.warehouse.validation.warehouse;

import com.example.warehouse.criteria.auth.UserCriteria;
import com.example.warehouse.dto.warehouse.WarehouseCreateDto;
import com.example.warehouse.dto.warehouse.WarehouseUpdateDto;
import com.example.warehouse.validation.AbstractValidation;
import com.example.warehouse.validation.BaseValidation;
import org.springframework.stereotype.Component;

@Component
public class WarehouseValidation extends AbstractValidation<WarehouseCreateDto, WarehouseUpdateDto> {
    @Override
    public void checkCreate(WarehouseCreateDto dto) {

    }

    @Override
    public void checkUpdate(WarehouseUpdateDto dto) {

    }

    @Override
    public void checkCriteria(UserCriteria criteria) {

    }
}
