package com.example.warehouse.dto.product;

import com.example.warehouse.dto.BaseGenericDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto extends BaseGenericDto {

    private String name;

    private String description;

    private String imagePath;

    private Double price;

    private String categoryId;
}
