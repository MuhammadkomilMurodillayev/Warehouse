package com.example.warehouse.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseGenericDto implements BaseDto{
    protected String id;
}
