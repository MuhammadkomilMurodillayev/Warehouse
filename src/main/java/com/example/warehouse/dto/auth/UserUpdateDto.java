package com.example.warehouse.dto.auth;

import com.example.warehouse.dto.BaseGenericDto;
import com.example.warehouse.enums.AuthGender;
import com.example.warehouse.enums.AuthRole;

import javax.validation.constraints.NotBlank;

public class UserUpdateDto extends BaseGenericDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String firstName;

    private String lastName;

    // @Pattern(regexp = "") TODO Write regex for phone number
    private String phone;

    private boolean superAdmin;

    private AuthGender gender;

    private AuthRole role;

    @NotBlank
    private String organizationId;

}
