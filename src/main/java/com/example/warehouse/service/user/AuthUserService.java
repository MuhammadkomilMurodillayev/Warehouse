package com.example.warehouse.service.user;

import com.example.warehouse.config.security.user.AuthUserDetails;
import com.example.warehouse.dto.auth.LoginDto;
import com.example.warehouse.dto.auth.SessionDto;
import com.example.warehouse.dto.data.DataDto;
import com.example.warehouse.entity.auth.User;
import com.example.warehouse.exception.BlockException;
import com.example.warehouse.properties.UrlProperties;
import com.example.warehouse.repository.user.UserRepository;
import com.example.warehouse.service.BaseService;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.example.warehouse.controller.AbstractController.PATH;

@Service
public class AuthUserService implements UserDetailsService, BaseService {


    private final RestTemplate restTemplate = new RestTemplate();

    private final UrlProperties urlProperties;
    private final ServerProperties serverProperties;
    private final UserRepository userRepository;

    public AuthUserService(UrlProperties urlProperties, ServerProperties serverProperties, UserRepository userRepository) {
        this.urlProperties = urlProperties;
        this.serverProperties = serverProperties;
        this.userRepository = userRepository;
    }


    public ResponseEntity<DataDto<SessionDto>> getToken(LoginDto dto) {

        String url = urlProperties.getProtocol() +
                "://" + urlProperties.getHost() +
                ":" + serverProperties.getPort() +
                PATH + "/auth/login";

        DataDto<SessionDto> sessionDto = restTemplate.postForObject(url, dto, DataDto.class);
//        assert sessionDto != null;
//        if (sessionDto.isSuccess())
        return new ResponseEntity<>(sessionDto, HttpStatus.OK);
//        throw new BadCredentialsException();

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user.getStatus() == 1)
            return new AuthUserDetails(user);
        else {
            throw new BlockException("you are blocked, you can`t login");
        }
    }
}
