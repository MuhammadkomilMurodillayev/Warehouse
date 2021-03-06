package com.example.warehouse.validation;

import com.example.warehouse.config.security.utils.UtilsForSessionUser;
import com.example.warehouse.criteria.BaseCriteria;
import com.example.warehouse.criteria.auth.UserCriteria;
import com.example.warehouse.dto.BaseDto;
import com.example.warehouse.dto.BaseGenericDto;

public abstract class AbstractValidation<CD extends BaseDto,UD extends BaseGenericDto,C extends BaseCriteria> implements BaseValidation{

    public static final UtilsForSessionUser sessionUser = null;


    public abstract void checkCreate(CD dto);

    public abstract void checkUpdate(UD dto);


    public abstract void checkCriteria(C criteria);

    public abstract void checkGet(String id);
}
