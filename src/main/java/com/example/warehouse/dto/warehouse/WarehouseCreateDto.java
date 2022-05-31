package com.example.warehouse.dto.warehouse;

import com.example.warehouse.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseCreateDto implements BaseDto {

    private String name;

    private String regionId;

    private String organizationId;

    private Double latitude;

    private Double longitude;

}
