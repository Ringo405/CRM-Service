package com.agilemonkeys.crm.api.web.controller.user;

import com.agilemonkeys.crm.api.application.dto.user.create.CreateUserCommand;
import com.agilemonkeys.crm.api.application.dto.user.create.CreateUserResponse;
import com.agilemonkeys.crm.api.application.dto.user.query.UserQuery;
import com.agilemonkeys.crm.api.application.dto.user.query.UserQueryResponse;
import com.agilemonkeys.crm.api.application.dto.user.query.UsersQueryResponse;
import com.agilemonkeys.crm.api.application.dto.user.update.UpdateUserCommand;
import com.agilemonkeys.crm.api.application.dto.user.update.UpdateUserResponse;
import com.agilemonkeys.crm.api.application.dto.user.update.UpdateUserRoleCommand;
import com.agilemonkeys.crm.api.application.service.UserService;
import com.agilemonkeys.crm.api.domain.valueobject.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        UsersQueryResponse usersQueryResponse = new UsersQueryResponse(Collections.emptyList());
        when(userService.getAllUsers()).thenReturn(usersQueryResponse);

        ResponseEntity<UsersQueryResponse> response = userController.getAllUsers();

        assertEquals(usersQueryResponse, response.getBody());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;
        UserQueryResponse userQueryResponse = new UserQueryResponse();
        when(userService.getUserById(any(UserQuery.class))).thenReturn(userQueryResponse);

        ResponseEntity<UserQueryResponse> response = userController.getUserById(userId);

        assertEquals(userQueryResponse, response.getBody());
        ArgumentCaptor<UserQuery> captor = ArgumentCaptor.forClass(UserQuery.class);
        verify(userService, times(1)).getUserById(captor.capture());
        assertEquals(userId, captor.getValue().getId());
    }

    @Test
    void testCreateUser() {
        CreateUserCommand command = new CreateUserCommand();

        CreateUserResponse createUserResponse = new CreateUserResponse();
        when(userService.createUser(command)).thenReturn(createUserResponse);

        ResponseEntity<CreateUserResponse> response = userController.createUser(command);

        assertEquals(createUserResponse, response.getBody());
        verify(userService, times(1)).createUser(command);
    }

    @Test
    void testUpdateUser() {
        Long userId = 1L;
        UpdateUserCommand command = new UpdateUserCommand();

        UpdateUserResponse updateUserResponse = new UpdateUserResponse();
        when(userService.updateUser(command)).thenReturn(updateUserResponse);

        ResponseEntity<UpdateUserResponse> response = userController.updateUser(userId, command);

        assertEquals(updateUserResponse, response.getBody());
        assertEquals(userId, command.getId());
        verify(userService, times(1)).updateUser(command);
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;

        userController.deleteUser(userId);

        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    void testUpdateUserRole() {
        Long userId = 1L;
        UpdateUserRoleCommand command = new UpdateUserRoleCommand(1L, Role.USER);
        UpdateUserResponse updateUserResponse = new UpdateUserResponse();
        when(userService.updateUserRole(command)).thenReturn(updateUserResponse);

        ResponseEntity<UpdateUserResponse> response = userController.updateUserRole(userId, command);

        assertEquals(updateUserResponse, response.getBody());
        assertEquals(userId, command.getId());
        verify(userService, times(1)).updateUserRole(command);
    }
}
