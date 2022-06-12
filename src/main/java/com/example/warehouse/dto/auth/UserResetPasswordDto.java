package com.example.warehouse.dto.auth;

import com.example.warehouse.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class UserResetPasswordDto implements BaseDto {

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;

}
