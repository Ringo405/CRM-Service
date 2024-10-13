package com.agilemonkeys.crm.api.application.service;

import com.agilemonkeys.crm.api.application.dto.user.create.CreateUserCommand;
import com.agilemonkeys.crm.api.application.dto.user.create.CreateUserResponse;
import com.agilemonkeys.crm.api.application.dto.user.query.UserQuery;
import com.agilemonkeys.crm.api.application.dto.user.query.UserQueryResponse;
import com.agilemonkeys.crm.api.application.dto.user.query.UsersQueryResponse;
import com.agilemonkeys.crm.api.application.dto.user.update.UpdateUserCommand;
import com.agilemonkeys.crm.api.application.dto.user.update.UpdateUserResponse;
import com.agilemonkeys.crm.api.application.dto.user.update.UpdateUserRoleCommand;


public interface UserService {
    UsersQueryResponse getAllUsers();

    UserQueryResponse getUserById(UserQuery userQuery);

    CreateUserResponse createUser(CreateUserCommand command);

    UpdateUserResponse updateUser(UpdateUserCommand command);

    void deleteUser(Long id);

    UpdateUserResponse updateUserRole(UpdateUserRoleCommand command);
}
