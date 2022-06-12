package com.example.warehouse.validation.warehouse;

import com.example.warehouse.criteria.warehouse.WarehouseCriteria;
import com.example.warehouse.dto.warehouse.WarehouseCreateDto;
import com.example.warehouse.dto.warehouse.WarehouseUpdateDto;
import com.example.warehouse.enums.AuthRole;
import com.example.warehouse.validation.AbstractValidation;
import org.springframework.stereotype.Component;

import static com.example.warehouse.config.security.utils.UtilsForSessionUser.*;

@Component
public class WarehouseValidation extends AbstractValidation<WarehouseCreateDto, WarehouseUpdateDto,WarehouseCriteria> {
    @Override
    public void checkCreate(WarehouseCreateDto dto) {

    }

    @Override
    public void checkUpdate(WarehouseUpdateDto dto) {

    }

    @Override
    public void checkCriteria(WarehouseCriteria criteria) {
        if (hasAnyRole(AuthRole.ADMIN,AuthRole.MANAGER)){
            criteria.setOrganizationId(getSessionUser().getOrganizationId());
        }
    }

    @Override
    public void checkGet(String id) {

    }
}
