package com.example.warehouse.dto.product;

import com.example.warehouse.dto.BaseGenericDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto extends BaseGenericDto {

    private String name;

    private String description;

    private String imagePath;

    private Double totalPrice;

    private Double price;

    private Integer count;

    private String categoryId;

    private String organizationId;

    private String organizationName;
}
