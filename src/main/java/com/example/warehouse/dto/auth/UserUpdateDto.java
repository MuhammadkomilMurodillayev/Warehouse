package com.example.warehouse.dto.auth;

import com.example.warehouse.dto.BaseGenericDto;
import com.example.warehouse.enums.Gender;
import com.example.warehouse.enums.AuthRole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserUpdateDto extends BaseGenericDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String firstName;

    private String lastName;

    // @Pattern(regexp = "") TODO Write regex for phone number
    private String phone;

    private Gender gender;

    private AuthRole role;

    @NotBlank
    private String organizationId;

}
