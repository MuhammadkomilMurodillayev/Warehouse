package com.example.warehouse;

import com.example.warehouse.dto.auth.UserCreateDto;
import com.example.warehouse.dto.organization.OrganizationCreateDto;
import com.example.warehouse.enums.AuthGender;
import com.example.warehouse.enums.AuthRole;
import com.example.warehouse.service.user.AuthUserService;
import com.example.warehouse.service.organization.OrganizationService;
import com.example.warehouse.service.user.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

@OpenAPIDefinition
@SpringBootApplication
public class WarehouseApplication {

    private final UserService userService;
    private final OrganizationService organizationService;

    public WarehouseApplication(UserService userService, OrganizationService organizationService) {
        this.userService = userService;
        this.organizationService = organizationService;
    }

    public static void main(String[] args) {
        SpringApplication.run(WarehouseApplication.class, args);
    }

    //  @Bean
    CommandLineRunner run() {
        return args -> {
            OrganizationCreateDto organizationCreateDto = OrganizationCreateDto.builder()
                    .name("TATU")
                    .description("nimadir")
                    .build();
            String newOrgId = organizationService.create(organizationCreateDto);

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            UserCreateDto dto = UserCreateDto
                    .builder()
                    .firstName("Muhammadkomil")
                    .lastName("Murodillayev")
                    .username("muhammad")
                    .password(passwordEncoder.encode("123"))
                    .role(AuthRole.ADMIN)
                    .gender(AuthGender.MALE)
                    .organizationId(newOrgId)
                    .build();
            userService.create(dto);
        };
    }
}
