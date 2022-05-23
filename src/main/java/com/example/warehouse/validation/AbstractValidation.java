package com.example.warehouse.validation;

import com.example.warehouse.dto.BaseDto;
import com.example.warehouse.dto.BaseGenericDto;

public abstract class AbstractValidation<CD extends BaseDto,UD extends BaseGenericDto> implements BaseValidation{

    public abstract void checkCreate(CD dto);

    public abstract void checkUpdate(UD dto);

}
