package com.example.warehouse.config.security.utils;

import com.example.warehouse.config.security.user.AuthUserDetails;
import com.example.warehouse.entity.auth.User;
import com.example.warehouse.enums.AuthRole;
import com.example.warehouse.repository.user.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;


@Component
public record UtilsForSessionUser(UserRepository repository) {

    public static AuthUserDetails getSessionUser() {
        return ((AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    public static boolean hasRole(AuthRole authRole) {

        String findRole = "ROLE_" + authRole.name();
        return getSessionUser().getAuthorities().stream().anyMatch((role -> role.toString().equalsIgnoreCase(findRole)));
    }

    public static boolean hasAnyRole(AuthRole... authRoles) {

        Optional<? extends GrantedAuthority> sesRoleOptional = getSessionUser().getAuthorities().stream().filter(role -> role.toString().startsWith("ROLE_")).findFirst();
        String sesRole = sesRoleOptional.get().toString().substring(5);
        return Arrays.stream(authRoles).anyMatch(authRole -> authRole.name().equalsIgnoreCase(sesRole));

    }


}
