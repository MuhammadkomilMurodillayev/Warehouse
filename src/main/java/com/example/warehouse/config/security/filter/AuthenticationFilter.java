package com.example.warehouse.config.security.filter;

import com.auth0.jwt.JWT;
import com.example.warehouse.config.security.jwt.JwtUtils;
import com.example.warehouse.dto.auth.LoginDto;
import com.example.warehouse.dto.auth.SessionDto;
import com.example.warehouse.dto.data.DataDto;
import com.example.warehouse.dto.error.AppErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.warehouse.controller.AbstractController.PATH;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;

    public AuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
        super.setFilterProcessesUrl(PATH + "/auth/login");

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        LoginDto loginDto = null;
        try {
            loginDto = objectMapper.readValue(request.getReader(), LoginDto.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword());

        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        Date expiryForToken = JwtUtils.getExpiry();

        List<String> authorities = user
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expiryForToken)
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", authorities)
                .sign(JwtUtils.getAlgorithm());

        SessionDto sessionDto = SessionDto.builder()
                .token(token)
                .tokenExpiry(expiryForToken.getTime())
                .issuedAt(System.currentTimeMillis())
                .role(getRole(authorities).substring(5))
                .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        objectMapper.writeValue(response.getOutputStream(), new DataDto<>(sessionDto));
    }

    private String getRole(List<String> authorities) {
        Optional<String> role = authorities.stream().filter((r) -> r.startsWith("ROLE")).findFirst();
        return role.orElse(null);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        DataDto<AppErrorDto> resp = new DataDto<>(
                AppErrorDto.builder()
                        .message(failed.getMessage())
                        .developerMessage(Arrays.toString(failed.getStackTrace()))
                        .status(HttpStatus.NOT_FOUND)
                        .build()
        );
        response.setStatus(404);
        objectMapper.writeValue(response.getOutputStream(), resp);
    }
}
