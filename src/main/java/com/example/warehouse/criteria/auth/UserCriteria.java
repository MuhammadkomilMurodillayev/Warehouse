package com.example.warehouse.criteria.auth;

import com.example.warehouse.criteria.AbstractCriteria;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCriteria extends AbstractCriteria {

    private String organizationId;

    private String warehouseId;

    public UserCriteria(Integer size, Integer page) {
        super(size, page);
    }
}
