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

    private String organizationId;

    public ProductCategory(String id, short status, String name, String code, String organizationId) {
        super(id, status);
        this.name = name;
        this.code = code;
        this.organizationId = organizationId;
    }

    public ProductCategory(String id, String name, String organizationId) {
        super.setId(id);
        this.name = name;
        this.organizationId = organizationId;
    }


}
