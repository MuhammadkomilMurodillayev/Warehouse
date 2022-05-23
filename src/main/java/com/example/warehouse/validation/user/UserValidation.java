package com.example.warehouse.validation.user;

import com.example.warehouse.dto.auth.UserCreateDto;
import com.example.warehouse.dto.auth.UserUpdateDto;
import com.example.warehouse.validation.AbstractValidation;
import org.springframework.stereotype.Component;

@Component
public class UserValidation extends AbstractValidation<UserCreateDto, UserUpdateDto> {

    @Override
    public void checkCreate(UserCreateDto dto) {

    }

    @Override
    public void checkUpdate(UserUpdateDto dto) {

    }
}
