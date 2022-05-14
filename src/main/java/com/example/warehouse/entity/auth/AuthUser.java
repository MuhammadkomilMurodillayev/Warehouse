package com.example.warehouse.entity.auth;

import com.example.warehouse.entity.Auditable;
import com.example.warehouse.enums.AuthGender;
import com.example.warehouse.enums.AuthRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthUser extends Auditable {

    private String username;

    private String password;

    private String firsName;

    private String lastName;

    private String fullName;

    private AuthRole role;

    private boolean superAdmin;

    private AuthGender gender;

    private String organizationId;

    private String warehouseId;


    public AuthUser() {
        super();
        this.fullName = this.firsName + " " + this.lastName;
        this.role = AuthRole.EMPLOYEE;
    }

}
