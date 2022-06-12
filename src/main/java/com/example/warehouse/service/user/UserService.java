package com.example.warehouse.service.user;

import com.example.warehouse.criteria.auth.UserCriteria;
import com.example.warehouse.dto.auth.UserCreateDto;
import com.example.warehouse.dto.auth.UserDto;
import com.example.warehouse.dto.auth.UserResetPasswordDto;
import com.example.warehouse.dto.auth.UserUpdateDto;
import com.example.warehouse.entity.auth.User;
import com.example.warehouse.exception.BadRequestException;
import com.example.warehouse.exception.ObjectNotFoundException;
import com.example.warehouse.mapper.user.UserMapper;
import com.example.warehouse.repository.user.UserRepository;
import com.example.warehouse.service.AbstractService;
import com.example.warehouse.service.BaseCrudService;
import com.example.warehouse.service.utils.UploadPhotoService;
import com.example.warehouse.validation.user.UserValidation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.example.warehouse.config.security.utils.UtilsForSessionUser.getSessionUser;

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
        String> {

    private final PasswordEncoder passwordEncoder;
    private final UploadPhotoService uploadPhotoService;

    protected UserService(UserRepository repository, UserMapper mapper, UserValidation validation, PasswordEncoder passwordEncoder, UploadPhotoService uploadPhotoService) {
        super(repository, mapper, validation);
        this.passwordEncoder = passwordEncoder;
        this.uploadPhotoService = uploadPhotoService;
    }

    @Override
    public UserDto get(String id) {
        User user = repository.findByIdNotDeleted(id);
        if (Objects.isNull(user))
            throw new ObjectNotFoundException();

        return mapper.toDto(user);
    }

    public UserDto get() {
        return get(getSessionUser().getId());
    }

    @Override
    public List<UserDto> getAll(UserCriteria criteria) {
        criteria.setOrganizationId(getSessionUser().getOrganizationId());
        validation.checkCriteria(criteria);
        List<User> users = repository.findAllNotDeleted(criteria);

        return mapper.toDto(users);
    }

    @Override
    public String create(UserCreateDto dto) {
        validation.checkCreate(dto);
        User user = mapper.fromCreateDto(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedBy(getSessionUser().getId());
        user.setUpdatedAt(user.getCreatedAt());
        user.setUpdatedBy(user.getCreatedBy());
        return repository.save(user).getId();
    }

    @Override
    public void update(UserUpdateDto dto) {
        validation.checkUpdate(dto);
        User user = repository.findByIdNotDeleted(dto.getId());
        user = mapper.fromUpdateDto(user, dto);
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(getSessionUser().getId());
        user.setFullName(user.getFirstName() + " " + user.getLastName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }

    @Override
    public void delete(String id) {
        repository.softDelete(id);
    }

    public void setImage(MultipartFile image) {
        String imagePath = uploadPhotoService.upload(image);
        repository.setImage(imagePath, getSessionUser().getId());
    }

    public void resetPassword(UserResetPasswordDto dto) {
        User user = repository.findByIdNotDeleted(getSessionUser().getId());

        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword()))
            throw new BadRequestException("old password incorrect");

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
    }
}
