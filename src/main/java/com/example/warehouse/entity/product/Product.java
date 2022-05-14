package com.example.warehouse.entity.product;

import com.example.warehouse.entity.Auditable;

public class Product extends Auditable {

    private String name;

    private String description;

    private String imagePath;

    private Double price;

    private String containerId;

}
