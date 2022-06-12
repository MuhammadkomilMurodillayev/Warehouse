package com.example.warehouse.config.security.user;

import com.example.warehouse.entity.auth.User;
import com.example.warehouse.enums.AuthRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
public class AuthUserDetails implements UserDetails {

    private final String id;
    private final String username;
    private final String password;
    private final short status;
    private final boolean deleted;
    private Set<GrantedAuthority> authorities;
    private final String organizationId;
    private final short organizationStatus;
    private final AuthRole role;


    public AuthUserDetails(User user, short organizationStatus) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.status = user.getStatus();
        this.deleted = user.isDeleted();
        this.authorities = processAuthorities(user.getRole());
        this.organizationId = user.getOrganizationId();
        this.organizationStatus = organizationStatus;
        this.role = user.getRole();
    }

    private Set<GrantedAuthority> processAuthorities(AuthRole role) {
        authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        return authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == 1 && organizationStatus == 1;
    }
}
