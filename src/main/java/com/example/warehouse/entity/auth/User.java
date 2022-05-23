package com.example.warehouse.entity.auth;

import com.example.warehouse.entity.Auditable;
import com.example.warehouse.enums.AuthGender;
import com.example.warehouse.enums.AuthRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Getter
@Setter
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class User extends Auditable {

    private String username;

    private String password;

    @JsonProperty(value = "firstname")
    private String firstName;

    @JsonProperty(value = "lastname")
    private String lastName;

    @JsonProperty(value = "auth_role")
    private String fullName;

    @JsonProperty(value = "superadmin")
    private boolean superAdmin;

    private AuthGender gender;

    @JsonProperty(value = "auth_role")
    private AuthRole role;

    @JsonProperty(value = "organization_id")
    private String organizationId;

    public User() {
        super();
        this.fullName = this.firstName + " " + this.lastName;
    }

}
