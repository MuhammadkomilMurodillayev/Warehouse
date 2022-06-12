package com.example.warehouse.dto.region;

import com.example.warehouse.dto.BaseGenericDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegionDto extends BaseGenericDto {

    private String name;

    public RegionDto(String id,String name) {
        super(id);
        this.name = name;
    }
}
