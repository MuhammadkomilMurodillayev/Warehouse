package com.example.warehouse.service.user;

import com.example.warehouse.criteria.auth.UserCriteria;
import com.example.warehouse.dto.auth.UserCreateDto;
import com.example.warehouse.dto.auth.UserDto;
import com.example.warehouse.dto.auth.UserUpdateDto;
import com.example.warehouse.entity.auth.User;
import com.example.warehouse.exception.UserNotFoundException;
import com.example.warehouse.mapper.user.UserMapper;
import com.example.warehouse.repository.user.UserRepository;
import com.example.warehouse.service.AbstractService;
import com.example.warehouse.service.BaseCrudService;
import com.example.warehouse.validation.user.UserValidation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService extends AbstractService<
        UserRepository,
        UserMapper,
        UserValidation>
        implements BaseCrudService<
        UserDto,
        UserUpdateDto,
        UserCreateDto,
        UserCriteria,
        String>{

    protected UserService(UserRepository repository, UserMapper mapper, UserValidation validation) {
        super(repository, mapper, validation);
    }

    @Override
    public UserDto get(String id) {
        User user = repository.findById(id);
        if (Objects.isNull(user))
            throw new UserNotFoundException();

        return mapper.toDto(user);
    }

    @Override
    public List<UserDto> getAll(UserCriteria criteria) {
        return null;
    }

    @Override
    public String create(UserCreateDto dto) {
        validation.checkCreate(dto);
        User user = mapper.fromCreateDto(dto);
        user.setCreatedBy("-1");
        return repository.save(user);
    }

    @Override
    public void update(UserUpdateDto dto) {

    }

    @Override
    public void delete(String id) {

    }
}
