package com.agilemonkeys.crm.api.application.service;

import com.agilemonkeys.crm.api.application.dto.user.query.UserQuery;
import com.agilemonkeys.crm.api.application.dto.user.query.UserQueryResponse;
import com.agilemonkeys.crm.api.application.dto.user.query.UsersQueryResponse;
import com.agilemonkeys.crm.api.application.dto.user.update.UpdateUserCommand;
import com.agilemonkeys.crm.api.application.dto.user.update.UpdateUserResponse;
import com.agilemonkeys.crm.api.application.dto.user.update.UpdateUserRoleCommand;
import com.agilemonkeys.crm.api.application.mapper.UserMapper;
import com.agilemonkeys.crm.api.application.service.impl.UserServiceImpl;
import com.agilemonkeys.crm.api.domain.user.User;
import com.agilemonkeys.crm.api.domain.valueobject.Password;
import com.agilemonkeys.crm.api.domain.valueobject.Role;
import com.agilemonkeys.crm.api.domain.valueobject.Username;
import com.agilemonkeys.crm.api.infrastructure.exception.NotFoundException;
import com.agilemonkeys.crm.api.infrastructure.persistance.entity.UserEntity;
import com.agilemonkeys.crm.api.infrastructure.persistance.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    public static User createTestUser() {
        return User.builder()
                .username(new Username("Pepe"))
                .password(new Password("Password123@"))
                .role(Role.ADMIN)
                .createdBy(1L)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testGetAllUsers() {
        // Given
        UserEntity mockEntity = new UserEntity();
        List<UserEntity> mockEntities = new ArrayList<>();
        mockEntities.add(mockEntity);

        when(userRepository.findAll()).thenReturn(mockEntities);
        when(userMapper.toDomain(mockEntity)).thenReturn(createTestUser());
        when(userMapper.toQueryResponse(any(User.class))).thenReturn(new UserQueryResponse());

        // When
        UsersQueryResponse response = userService.getAllUsers();

        // Then
        assertNotNull(response);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        // Given
        Long userId = 1L;
        UserEntity mockEntity = new UserEntity();
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockEntity));
        when(userMapper.toDomain(mockEntity)).thenReturn(createTestUser());
        when(userMapper.toQueryResponse(any(User.class))).thenReturn(new UserQueryResponse());

        UserQuery query = new UserQuery(userId);

        // When
        UserQueryResponse response = userService.getUserById(query);

        // Then
        assertNotNull(response);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testUpdateUser() {
        // Given
        Long userId = 1L;
        UpdateUserCommand command = new UpdateUserCommand();
        command.setId(userId);
        command.setUsername("newusername");
        command.setPassword("newpassword");

        UserEntity existingEntity = new UserEntity();
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingEntity));
        when(userRepository.findByUsername(command.getUsername())).thenReturn(Optional.empty());

        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode(command.getPassword())).thenReturn(encodedPassword);
        existingEntity.setPassword(encodedPassword);

        when(userRepository.save(existingEntity)).thenReturn(existingEntity);
        when(userMapper.toDomain(existingEntity)).thenReturn(createTestUser());
        when(userMapper.toUpdateResponse(any(User.class))).thenReturn(new UpdateUserResponse());

        // When
        UpdateUserResponse response = userService.updateUser(command);

        // Then
        assertNotNull(response);
        verify(userRepository, times(1)).save(existingEntity);
    }

    @Test
    void testDeleteUser() {
        // Given
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        // When
        userService.deleteUser(userId);

        // Then
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUserNotFound() {
        // Given
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);

        // When & Then
        assertThrows(NotFoundException.class, () -> userService.deleteUser(userId));
    }

    @Test
    void testUpdateUserRole() {
        // Given
        Long userId = 1L;
        UpdateUserRoleCommand command = new UpdateUserRoleCommand(1L, Role.USER);

        UserEntity userEntity = new UserEntity();
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toDomain(userEntity)).thenReturn(createTestUser());
        when(userMapper.toUpdateResponse(any(User.class))).thenReturn(new UpdateUserResponse());

        // When
        UpdateUserResponse response = userService.updateUserRole(command);

        // Then
        assertNotNull(response);
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void testUpdateUserRoleNotFound() {
        // Given
        Long userId = 1L;
        UpdateUserRoleCommand command = new UpdateUserRoleCommand(1L, Role.USER);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> userService.updateUserRole(command));
    }
}
