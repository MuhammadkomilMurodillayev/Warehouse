package com.example.warehouse.validation.organization;

import com.example.warehouse.criteria.auth.UserCriteria;
import com.example.warehouse.criteria.organization.OrganizationCriteria;
import com.example.warehouse.dto.organization.OrganizationCreateDto;
import com.example.warehouse.dto.organization.OrganizationUpdateDto;
import com.example.warehouse.enums.AuthRole;
import com.example.warehouse.exception.BadRequestException;
import com.example.warehouse.exception.NullElementException;
import com.example.warehouse.exception.PermissionDenied;
import com.example.warehouse.validation.AbstractValidation;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.example.warehouse.config.security.utils.UtilsForSessionUser.getSessionUser;
import static com.example.warehouse.config.security.utils.UtilsForSessionUser.hasRole;

@Component
public class OrganizationValidation extends AbstractValidation<OrganizationCreateDto, OrganizationUpdateDto,OrganizationCriteria> {

    @Override
    public void checkCreate(OrganizationCreateDto dto) {
        if (!hasRole(AuthRole.SUPER_ADMIN))
            throw new PermissionDenied();
        if (Objects.isNull(dto.getName()))
            throw new NullElementException(dto.getName());
    }

    @Override
    public void checkUpdate(OrganizationUpdateDto dto) {
        if (hasRole(AuthRole.ADMIN) && !dto.getId().equals(getSessionUser().getOrganizationId()))
            throw new PermissionDenied();
    }

    @Override
    public void checkCriteria(OrganizationCriteria criteria) {

    }

    @Override
    public void checkGet(String id) {
        if (!hasRole(AuthRole.SUPER_ADMIN) && (hasRole(AuthRole.ADMIN) && !getSessionUser().getOrganizationId().equals(id)))
            throw new PermissionDenied();
    }

}
