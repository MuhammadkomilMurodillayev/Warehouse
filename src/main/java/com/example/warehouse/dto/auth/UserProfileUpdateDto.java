package com.example.warehouse.dto.auth;

import com.example.warehouse.dto.BaseDto;
import com.example.warehouse.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserProfileUpdateDto implements BaseDto {

    private String firstName;

    private String lastName;

    // @Pattern(regexp = "") TODO Write regex for phone number
    private String phone;

    private Gender gender;

    private MultipartFile image;

}
