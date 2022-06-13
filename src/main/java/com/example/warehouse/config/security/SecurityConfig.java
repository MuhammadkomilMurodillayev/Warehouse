package com.example.warehouse.config.security;

import com.example.warehouse.config.security.filter.AuthenticationFilter;
import com.example.warehouse.config.security.filter.AuthorizationFilter;
import com.example.warehouse.service.user.AuthUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.warehouse.controller.AbstractController.PATH;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public final static String[] WHITE_LIST = {
            "/sw",
            "/api/v1/auth/token",
            "/token",
            "/swagger-ui/**",
            "/api-docs/**",
            "/api/v1/organization/**"
        };


    private final AuthUserService authUserService;
    private final ObjectMapper objectMapper;
    private final AuthorizationFilter authorizationFilter;
    private final PasswordEncoder passwordEncoder;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .userDetailsService(authUserService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests(expressionInterceptUrlRegistry ->
                expressionInterceptUrlRegistry
                        .antMatchers(WHITE_LIST)
                        .permitAll()
                        .anyRequest()
                        .authenticated());

        http.addFilter(new AuthenticationFilter(authenticationManager(), objectMapper));
        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

    }





    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}

