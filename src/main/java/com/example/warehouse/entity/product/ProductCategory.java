package com.example.warehouse.entity.product;

import com.example.warehouse.entity.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductCategory extends Auditable {

    private String name;

    private String code;

}
