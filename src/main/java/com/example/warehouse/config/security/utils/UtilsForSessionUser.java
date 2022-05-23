package com.example.warehouse.config.security.utils;

import com.example.warehouse.entity.auth.User;
import com.example.warehouse.repository.user.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public record UtilsForSessionUser(UserRepository repository) {

    public  String getSessionId() {
        User byUserName = repository
                .findByUserName(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return byUserName.getId();
    }

}
