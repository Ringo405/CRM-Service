package com.agilemonkeys.crm.api.application.service.impl;

import com.agilemonkeys.crm.api.application.dto.user.create.CreateUserCommand;
import com.agilemonkeys.crm.api.application.dto.user.create.CreateUserResponse;
import com.agilemonkeys.crm.api.application.dto.user.query.UserQuery;
import com.agilemonkeys.crm.api.application.dto.user.query.UserQueryResponse;
import com.agilemonkeys.crm.api.application.dto.user.query.UsersQueryResponse;
import com.agilemonkeys.crm.api.application.dto.user.update.UpdateUserCommand;
import com.agilemonkeys.crm.api.application.dto.user.update.UpdateUserResponse;
import com.agilemonkeys.crm.api.application.mapper.UserMapper;
import com.agilemonkeys.crm.api.domain.user.User;
import com.agilemonkeys.crm.api.infrastructure.exception.NotFoundException;
import com.agilemonkeys.crm.api.infrastructure.model.UserEntity;
import com.agilemonkeys.crm.api.infrastructure.repository.UserRepository;
import com.agilemonkeys.crm.api.application.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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
                .orElseThrow(() -> new NotFoundException("User not found"));

        User savedUserDomain = userMapper.toDomain(userEntity);
        return userMapper.toQueryResponse(savedUserDomain);
    }

    @Override
    public CreateUserResponse createUser(CreateUserCommand command) {
        User user = userMapper.toDomain(command);

        user.initialize();
        user.validate();

        UserEntity userEntity = userMapper.toEntity(user);
        UserEntity savedEntity = userRepository.save(userEntity);
        return userMapper.toCreateResponse(userMapper.toDomain(savedEntity));
    }

    @Override
    public UpdateUserResponse updateUser(UpdateUserCommand command) {
        UserEntity existingEntity = userRepository.findById(command.getId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (command.getUsername() != null) {
            existingEntity.setUsername(command.getUsername());
        }
        if (command.getPassword() != null && !command.getPassword().isEmpty()) {
            existingEntity.setPassword(command.getPassword());
        }
        if (command.getRole() != null) {
            existingEntity.setRole(command.getRole().getDescription());
        }
        existingEntity.setUpdatedAt(LocalDateTime.now());
        //existingEntity.setUpdatedBy(command.getUpdatedBy()); lo ha de coger del user logeado

        UserEntity savedEntity = userRepository.save(existingEntity);
        User savedUserDomain = userMapper.toDomain(savedEntity);
        return userMapper.toUpdateResponse(savedUserDomain);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public User updateUserRole(Long id, String role) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        userEntity.setRole(role);
        UserEntity updatedEntity = userRepository.save(userEntity);
        return userMapper.toDomain(updatedEntity);
    }
}
