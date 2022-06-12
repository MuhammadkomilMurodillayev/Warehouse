package com.example.warehouse.entity.product;

import com.example.warehouse.entity.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends Auditable {

    private String name;

    private String description;

    private String imagePath;

    private Double price;

    private Integer count;

    private String categoryId;

    private Double totalPrice;

}
