package com.example.warehouse.criteria.warehouse;

import com.example.warehouse.criteria.AbstractCriteria;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseCriteria extends AbstractCriteria {

    private String organizationId;

    public WarehouseCriteria(Integer size, Integer page) {
        super(size, page);
    }
}
