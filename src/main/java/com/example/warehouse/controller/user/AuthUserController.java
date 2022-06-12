package com.example.warehouse.controller.user;

import com.example.warehouse.controller.BaseController;
import com.example.warehouse.dto.auth.LoginDto;
import com.example.warehouse.dto.auth.SessionDto;
import com.example.warehouse.dto.data.DataDto;
import com.example.warehouse.service.user.AuthUserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.HttpResource;

import static com.example.warehouse.controller.AbstractController.PATH;

@RequiredArgsConstructor
@RestController
@RequestMapping(PATH + "/auth")
public class AuthUserController implements BaseController {

    private final AuthUserService service;

    @PostMapping("/token")
    public ResponseEntity<DataDto<SessionDto>> getToken(@RequestBody LoginDto dto) {
        return service.getToken(dto);
    }

    @Operation(summary = "no working for now🤕")
    @PostMapping("/refreshToken")
    public ResponseEntity<DataDto<SessionDto>> refreshToken(HttpRequest request, HttpResource response) {
        return null;
    }
}
