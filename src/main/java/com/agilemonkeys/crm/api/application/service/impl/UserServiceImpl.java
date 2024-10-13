package com.agilemonkeys.crm.api.application.service.impl;

import com.agilemonkeys.crm.api.application.dto.user.create.CreateUserCommand;
import com.agilemonkeys.crm.api.application.dto.user.create.CreateUserResponse;
import com.agilemonkeys.crm.api.application.dto.user.query.UserQuery;
import com.agilemonkeys.crm.api.application.dto.user.query.UserQueryResponse;
import com.agilemonkeys.crm.api.application.dto.user.query.UsersQueryResponse;
import com.agilemonkeys.crm.api.application.dto.user.update.UpdateUserCommand;
import com.agilemonkeys.crm.api.application.dto.user.update.UpdateUserResponse;
import com.agilemonkeys.crm.api.application.dto.user.update.UpdateUserRoleCommand;
import com.agilemonkeys.crm.api.application.mapper.UserMapper;
import com.agilemonkeys.crm.api.domain.user.User;
import com.agilemonkeys.crm.api.domain.valueobject.Role;
import com.agilemonkeys.crm.api.infrastructure.exception.NotFoundException;
import com.agilemonkeys.crm.api.infrastructure.model.UserEntity;
import com.agilemonkeys.crm.api.infrastructure.repository.UserRepository;
import com.agilemonkeys.crm.api.application.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.agilemonkeys.crm.api.infrastructure.exception.ErrorMessages.USER_NOT_FOUND;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsersQueryResponse getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserQueryResponse> userResponses = userEntities.stream()
                .map(userMapper::toDomain)
                .map(userMapper::toQueryResponse)
                .collect(Collectors.toList());

        return new UsersQueryResponse(userResponses);
    }

    @Override
    public UserQueryResponse getUserById(UserQuery userQuery) {
        UserEntity userEntity = userRepository.findById(userQuery.getId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        User savedUserDomain = userMapper.toDomain(userEntity);
        return userMapper.toQueryResponse(savedUserDomain);
    }

    @Override
    public CreateUserResponse createUser(CreateUserCommand command) {
        User user = userMapper.toDomain(command);

        user.initialize();
        user.validate();

        if (userRepository.findByUsername(user.getUsername().getValue()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        UserEntity userEntity = userMapper.toEntity(user);
        String encodedPassword = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(encodedPassword);
        UserEntity savedEntity = userRepository.save(userEntity);
        return userMapper.toCreateResponse(userMapper.toDomain(savedEntity));
    }

    @Override
    public UpdateUserResponse updateUser(UpdateUserCommand command) {
        UserEntity existingEntity = userRepository.findById(command.getId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        if (command.getUsername() != null && userRepository.findByUsername(command.getUsername()).isEmpty()) {
            existingEntity.setUsername(command.getUsername());
        } else {
            throw new IllegalArgumentException("Username already exists");
        }
        if (command.getPassword() != null && !command.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(command.getPassword());
            existingEntity.setPassword(encodedPassword);
        }
        if (command.getRole() != null) {
            existingEntity.setRole(String.valueOf(command.getRole()));
        }

        UserEntity savedEntity = userRepository.save(existingEntity);
        User savedUserDomain = userMapper.toDomain(savedEntity);
        return userMapper.toUpdateResponse(savedUserDomain);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

    @Override
    public UpdateUserResponse updateUserRole(UpdateUserRoleCommand command) {
        UserEntity userEntity = userRepository.findById(command.getId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        Role role = Role.valueOf(command.getRole().name());
        userEntity.setRole(role.name());

        UserEntity updatedEntity = userRepository.save(userEntity);
        User savedUserDomain = userMapper.toDomain(updatedEntity);

        return userMapper.toUpdateResponse(savedUserDomain);
    }
}
