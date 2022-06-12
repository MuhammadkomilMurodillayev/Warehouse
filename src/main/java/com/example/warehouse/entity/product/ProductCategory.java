package com.example.warehouse.entity.product;

import com.example.warehouse.entity.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ProductCategory extends Auditable {

    private String name;

    private String code;

    private String warehouseId;

    public ProductCategory(String id, short status, String name, String code, String warehouseId) {
        super(id, status);
        this.name = name;
        this.code = code;
        this.warehouseId = warehouseId;
    }

    public ProductCategory(String id, String name, String warehouseId) {
        super.setId(id);
        this.name = name;
        this.warehouseId = warehouseId;
    }


}
