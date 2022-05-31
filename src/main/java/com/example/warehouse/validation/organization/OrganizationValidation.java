package com.example.warehouse.validation.organization;

import com.example.warehouse.criteria.auth.UserCriteria;
import com.example.warehouse.dto.organization.OrganizationCreateDto;
import com.example.warehouse.dto.organization.OrganizationUpdateDto;
import com.example.warehouse.enums.AuthRole;
import com.example.warehouse.exception.NullElementException;
import com.example.warehouse.exception.PermissionDenied;
import com.example.warehouse.validation.AbstractValidation;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.example.warehouse.config.security.utils.UtilsForSessionUser.hasRole;

@Component
public class OrganizationValidation extends AbstractValidation<OrganizationCreateDto, OrganizationUpdateDto> {

    @Override
    public void checkCreate(OrganizationCreateDto dto) {
        if (!hasRole(AuthRole.SUPER_ADMIN))
            throw new PermissionDenied();
        if (Objects.isNull(dto.getName()))
            throw new NullElementException(dto.getName());
    }

    @Override
    public void checkUpdate(OrganizationUpdateDto dto) {
        if (!hasRole(AuthRole.SUPER_ADMIN) && !hasRole(AuthRole.ADMIN))
            throw new PermissionDenied();
    }

    @Override
    public void checkCriteria(UserCriteria criteria) {

    }

}
