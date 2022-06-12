package com.example.warehouse.dto.auth;

import com.example.warehouse.dto.BaseGenericDto;
import com.example.warehouse.enums.Gender;
import com.example.warehouse.enums.AuthRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class UserUpdateDto extends BaseGenericDto {

    @NotBlank
    private String username;

    private String firstName;

    private String lastName;

    // @Pattern(regexp = "") TODO Write regex for phone number
    private String phone;

    private Gender gender;

    private AuthRole role;

    @NotBlank
    private String organizationId;



}
