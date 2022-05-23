package com.example.warehouse.dto.organization;

import com.example.warehouse.dto.BaseGenericDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationDto extends BaseGenericDto {

    private String name;

    private String description;

    private String logoPath;

}
