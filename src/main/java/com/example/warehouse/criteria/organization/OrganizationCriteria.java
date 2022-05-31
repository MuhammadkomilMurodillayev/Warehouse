package com.example.warehouse.criteria.organization;

import com.example.warehouse.criteria.AbstractCriteria;
import lombok.ToString;

@ToString
public class OrganizationCriteria extends AbstractCriteria {

    public OrganizationCriteria(Integer size, Integer page) {
        super(size, page);
    }
}
