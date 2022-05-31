package com.example.warehouse.dto.warehouse;

import com.example.warehouse.dto.BaseGenericDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseUpdateDto extends BaseGenericDto {

    private String name;

    private String regionId;

    private Double latitude;

    private Double longitude;

}
