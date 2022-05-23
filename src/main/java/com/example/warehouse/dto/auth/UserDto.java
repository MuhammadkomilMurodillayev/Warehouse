package com.example.warehouse.dto.auth;

import com.example.warehouse.dto.BaseGenericDto;
import com.example.warehouse.enums.AuthGender;
import com.example.warehouse.enums.AuthRole;

public class UserDto extends BaseGenericDto {

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String fullName;

    private boolean superAdmin;

    private AuthGender gender;

    private AuthRole role;

    private String organizationId;

}
