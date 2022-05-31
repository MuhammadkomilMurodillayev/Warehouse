package com.example.warehouse.criteria;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
public class AbstractCriteria implements BaseCriteria {

    protected Integer size;
    protected Integer page;

    public AbstractCriteria(Integer size, Integer page) {
        this.size = size;
        this.page = page;
    }

    public AbstractCriteria(Integer page) {
        this(5, page);
    }

}
