package com.example.warehouse.validation.user;

import com.example.warehouse.criteria.auth.UserCriteria;
import com.example.warehouse.dto.auth.UserCreateDto;
import com.example.warehouse.dto.auth.UserResetPasswordDto;
import com.example.warehouse.dto.auth.UserUpdateDto;
import com.example.warehouse.entity.auth.User;
import com.example.warehouse.enums.AuthRole;
import com.example.warehouse.exception.AlreadyTakenException;
import com.example.warehouse.exception.BadRequestException;
import com.example.warehouse.exception.PermissionDenied;
import com.example.warehouse.repository.user.UserRepository;
import com.example.warehouse.validation.AbstractValidation;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.example.warehouse.config.security.utils.UtilsForSessionUser.*;

@Component
public class UserValidation extends AbstractValidation<UserCreateDto, UserUpdateDto,UserCriteria> {

    private final UserRepository userRepository;

    public UserValidation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void checkCreate(UserCreateDto dto) {
        if (!hasRole(AuthRole.SUPER_ADMIN)) {
            if (hasRole(AuthRole.ADMIN)) {
                if (!createUserRoleAnyMatch(dto.getRole(), AuthRole.EMPLOYEE, AuthRole.MANAGER)) {
                    throw new PermissionDenied();
                }
            } else if (hasRole(AuthRole.MANAGER)) {
                if (!createUserRoleMatch(dto.getRole(), AuthRole.EMPLOYEE)) {
                    throw new PermissionDenied();
                }
            } else throw new PermissionDenied();
        }
        if (!userRepository.usernameUnique(dto.getUsername()))
           throw new AlreadyTakenException(dto.getUsername() + " username");
    }

    @Override
    public void checkUpdate(UserUpdateDto dto) {
        if (!hasRole(AuthRole.SUPER_ADMIN) && !hasRole(AuthRole.ADMIN) && !dto.getId().equals(getSessionUser().getId()))
            throw new PermissionDenied();
    }

    @Override
    public void checkCriteria(UserCriteria criteria) {
        if (!hasAnyRole(AuthRole.SUPER_ADMIN) && criteria.getWarehouseId() == null && criteria.getOrganizationId() == null) {
            throw new PermissionDenied();
        }
    }

    @Override
    public void checkGet(String id) {

    }


    private boolean createUserRoleAnyMatch(AuthRole userRole, AuthRole... validateRoles) {
        return Arrays.stream(validateRoles).anyMatch(authRole -> authRole.name().equalsIgnoreCase(userRole.name()));
    }

    public static boolean createUserRoleMatch(AuthRole userRole, AuthRole validateRole) {
        return userRole.name().equalsIgnoreCase(validateRole.name());
    }

}
