package com.example.warehouse.config.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.warehouse.config.security.user.AuthUserDetails;
import com.example.warehouse.config.security.jwt.JwtUtils;
import com.example.warehouse.dto.data.DataDto;
import com.example.warehouse.dto.error.AppErrorDto;
import com.example.warehouse.repository.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import static com.example.warehouse.controller.AbstractController.PATH;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;
    private final ObjectMapper mapper;

    public AuthorizationFilter(UserRepository userRepository, ObjectMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean isTokenPath = request.getServletPath().equals(PATH + "/auth/token");
        boolean isLoginPath = request.getServletPath().equals(PATH + "/auth/login");
        if (isTokenPath || isLoginPath) {
            filterChain.doFilter(request, response);
            return;
        }
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (Objects.nonNull(header) && header.startsWith("Bearer ")) {
            try {

                String token = header.substring(7);
                DecodedJWT decodedJWT = JwtUtils.getVerifier().verify(token);
                String username = decodedJWT.getSubject();
                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

                Collection<GrantedAuthority> authorities = new ArrayList<>();

                Arrays.asList(roles).forEach(
                        (role) -> authorities.add(new SimpleGrantedAuthority(role))
                );

                AuthUserDetails authUserDetails = new AuthUserDetails(userRepository.findByUserName(username));

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(authUserDetails, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                DataDto<AppErrorDto> responseDTO = new DataDto<>(new AppErrorDto(HttpStatus.FORBIDDEN, e.getLocalizedMessage(), request.getRequestURI()));
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                mapper.writeValue(response.getOutputStream(), responseDTO);
            }
        } else
            filterChain.doFilter(request, response);

    }
}
