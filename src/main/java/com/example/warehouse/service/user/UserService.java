package com.example.warehouse.service.user;

import com.example.warehouse.criteria.auth.UserCriteria;
import com.example.warehouse.dto.auth.*;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.example.warehouse.config.security.utils.UtilsForSessionUser.getSessionUser;
import static com.example.warehouse.config.security.utils.UtilsForSessionUser.hasRole;

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
        switch (getSessionUser().getRole().name()) {
            case "ADMIN" -> users.removeIf((user) ->
                    user.getRole().name().equals("ADMIN")
            );
            case "MANAGER" -> users.removeIf((user) ->
                    user.getRole().name().equals("ADMIN") || user.getRole().name().equals("MANAGER")
            );
        }
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
        String newId = repository.save(user).getId();
        if (dto.getWarehouseId() != null)
            repository.saveRelationUserAndWarehouse(dto.getWarehouseId(), newId);
        return newId;
    }

    @Override
    public void update(UserUpdateDto dto) {
        validation.checkUpdate(dto);
        User user = repository.findByIdNotDeleted(dto.getId());
        if (dto.getOrganizationId() == null)
            dto.setOrganizationId(user.getOrganizationId());
        user = mapper.fromUpdateDto(user, dto);
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(getSessionUser().getId());
        user.setFullName(user.getFirstName() + " " + user.getLastName());
        if (dto.getWarehouseId() != null && !dto.getWarehouseId().equals(""))
            repository.saveRelationUserAndWarehouse(dto.getWarehouseId(), user.getId());
        repository.save(user);
    }

    public void update(UserProfileUpdateDto dto) {
        validation.checkUpdate(dto);
        User user = repository.findByIdNotDeleted(getSessionUser().getId());
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(getSessionUser().getId());
        user.setPhone(dto.getPhone());
        user.setLastName(dto.getLastName());
        user.setImagePath(uploadPhotoService.upload(dto.getImage()));
        user.setGender(dto.getGender());
        user.setFirstName(dto.getFirstName());
        user.setFullName(user.getFirstName() + " " + user.getLastName());
        repository.save(user);
    }

    @Override
    public void delete(String id) {
        repository.softDelete(id);
    }


    public void resetPassword(UserResetPasswordDto dto) {

        User user = repository.findByIdNotDeleted(getSessionUser().getId());

        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword()))
            throw new BadRequestException("old password incorrect");

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));

        repository.save(user);
    }

}
