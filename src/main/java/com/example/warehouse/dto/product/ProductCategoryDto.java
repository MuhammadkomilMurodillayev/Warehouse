package com.example.warehouse.dto.product;

import com.example.warehouse.dto.BaseGenericDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCategoryDto extends BaseGenericDto {

    private String name;

    private String organizationId;
}
