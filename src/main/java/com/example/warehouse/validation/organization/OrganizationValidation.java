package com.example.warehouse.validation.organization;

import com.example.warehouse.dto.organization.OrganizationCreateDto;
import com.example.warehouse.dto.organization.OrganizationUpdateDto;
import com.example.warehouse.exception.NullElementException;
import com.example.warehouse.validation.AbstractValidation;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OrganizationValidation extends AbstractValidation<OrganizationCreateDto, OrganizationUpdateDto> {

    @Override
    public void checkCreate(OrganizationCreateDto dto) {
        if (Objects.isNull(dto.getName()))
            throw new NullElementException(dto.getName());
    }

    @Override
    public void checkUpdate(OrganizationUpdateDto dto) {

    }
}
