package com.example.warehouse.dto.auth;

import com.example.warehouse.dto.BaseGenericDto;
import com.example.warehouse.enums.Gender;
import com.example.warehouse.enums.AuthRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto extends BaseGenericDto {

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String fullName;

    private Gender gender;

    private String phone;

    private AuthRole role;


    private String organizationId;

}
