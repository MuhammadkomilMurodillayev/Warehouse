package com.example.warehouse.mapper.warehouse;

import com.example.warehouse.dto.warehouse.WarehouseCreateDto;
import com.example.warehouse.dto.warehouse.WarehouseDto;
import com.example.warehouse.dto.warehouse.WarehouseUpdateDto;
import com.example.warehouse.entity.warehouse.Warehouse;
import com.example.warehouse.mapper.BaseGenericMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface WarehouseMapper extends BaseGenericMapper<
        Warehouse,
        WarehouseDto,
        WarehouseCreateDto,
        WarehouseUpdateDto> {

    @Override
    Warehouse fromCreateDto(WarehouseCreateDto dto);

    @Override
    Warehouse fromUpdateDto(WarehouseUpdateDto dto);

    @Override
    WarehouseDto toDto(Warehouse entity);
}
