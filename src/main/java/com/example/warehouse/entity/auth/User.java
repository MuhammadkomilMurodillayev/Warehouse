package com.example.warehouse.entity.auth;

import com.example.warehouse.entity.Auditable;
import com.example.warehouse.enums.Gender;
import com.example.warehouse.enums.AuthRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends Auditable {

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String fullName;

    private String phone;

    private Gender gender;

    private String imagePath;

    private AuthRole role;

    private String organizationId;


    public User(String id, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy, boolean deleted, short status, String username, String password, String firstName, String lastName, String fullName, Gender gender, AuthRole role, String organizationId, String phone) {
        super(id, createdAt, createdBy, updatedAt, updatedBy, deleted, status);
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.gender = gender;
        this.role = role;
        this.organizationId = organizationId;
        this.phone = phone;
    }
}
