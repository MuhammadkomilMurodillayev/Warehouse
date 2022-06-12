package com.example.warehouse.dto.organization;

import com.example.warehouse.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrganizationLogoDto implements BaseDto {

    private String organizationName;

    private String logoPath;

}
