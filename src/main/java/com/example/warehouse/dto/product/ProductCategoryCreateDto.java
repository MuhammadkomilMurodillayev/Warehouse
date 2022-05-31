package com.example.warehouse.dto.product;

import com.example.warehouse.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryCreateDto implements BaseDto {
    @NotBlank
    private String name;

    @NotBlank
    private String organizationId;
}
