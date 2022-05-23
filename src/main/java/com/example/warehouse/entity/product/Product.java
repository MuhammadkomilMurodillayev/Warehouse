package com.example.warehouse.entity.product;

import com.example.warehouse.entity.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Product extends Auditable {

    private String name;

    private String description;

    private String imagePath;

    private Double price;

    private String containerId;

}
