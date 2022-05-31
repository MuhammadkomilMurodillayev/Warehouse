package com.example.warehouse.dto.product;

import com.example.warehouse.dto.BaseGenericDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ProductCategoryUpdateDto extends BaseGenericDto {
    @NotBlank
    private String name;

}
