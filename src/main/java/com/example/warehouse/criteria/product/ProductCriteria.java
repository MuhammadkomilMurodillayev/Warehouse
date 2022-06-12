package com.example.warehouse.criteria.product;

import com.example.warehouse.criteria.AbstractCriteria;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCriteria extends AbstractCriteria {

    private String categoryId;

    private String organizationId;

    public ProductCriteria(Integer size, Integer page) {
        super(size, page);
    }
}
