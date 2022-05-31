package com.example.warehouse.criteria.product;

import com.example.warehouse.criteria.BaseCriteria;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCategoryCriteria implements BaseCriteria {
    private String organizationId;
}
