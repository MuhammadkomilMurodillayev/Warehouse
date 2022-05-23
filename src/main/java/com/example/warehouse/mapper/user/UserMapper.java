package com.example.warehouse.mapper.user;

import com.example.warehouse.dto.auth.UserCreateDto;
import com.example.warehouse.dto.auth.UserDto;
import com.example.warehouse.dto.auth.UserUpdateDto;
import com.example.warehouse.entity.auth.User;
import com.example.warehouse.mapper.BaseGenericMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper extends BaseGenericMapper<
        User,
        UserDto,
        UserCreateDto,
        UserUpdateDto> {

    @Override
    User fromCreateDto(UserCreateDto dto);

    @Override
    User fromUpdateDto(UserUpdateDto dto);

    @Override
    UserDto toDto(User entity);
}
