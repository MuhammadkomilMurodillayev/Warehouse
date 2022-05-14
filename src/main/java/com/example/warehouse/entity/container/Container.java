package com.example.warehouse.entity.container;

import com.example.warehouse.entity.Auditable;
import com.example.warehouse.entity.product.ProductCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Container extends Auditable {

   private String wareHouseId;

   private ProductCategory category;

   public Container() {
        super();
    }

}