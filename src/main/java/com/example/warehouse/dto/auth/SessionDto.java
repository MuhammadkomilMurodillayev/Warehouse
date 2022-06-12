package com.example.warehouse.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessionDto implements Serializable {

    private Long tokenExpiry;

    private Long issuedAt;

    private String token;

    private String role;

    private String logoPath;

}
