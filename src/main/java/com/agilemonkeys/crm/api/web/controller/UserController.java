package com.agilemonkeys.crm.api.web.controller;

import com.agilemonkeys.crm.api.application.dto.user.create.CreateUserCommand;
import com.agilemonkeys.crm.api.application.dto.user.create.CreateUserResponse;
import com.agilemonkeys.crm.api.application.dto.user.query.UserQuery;
import com.agilemonkeys.crm.api.application.dto.user.query.UserQueryResponse;
import com.agilemonkeys.crm.api.application.dto.user.query.UsersQueryResponse;
import com.agilemonkeys.crm.api.application.dto.user.update.UpdateUserCommand;
import com.agilemonkeys.crm.api.application.dto.user.update.UpdateUserResponse;
import com.agilemonkeys.crm.api.domain.user.User;
import com.agilemonkeys.crm.api.application.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UsersQueryResponse> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserQueryResponse> getUserById(@PathVariable Long id) {
        UserQuery userQuery = UserQuery.builder().id(id).build();
        return ResponseEntity.ok(userService.getUserById(userQuery));
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserCommand command) {
        return ResponseEntity.ok(userService.createUser(command));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateUserResponse> updateUser(@PathVariable Long id, @RequestBody UpdateUserCommand command) {
        command.setId(id);
        return ResponseEntity.ok(userService.updateUser(command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<User> updateUserRole(@PathVariable Long id, @RequestParam String role) {
        /*if (!"ROLE_ADMIN".equals(role) && !"ROLE_USER".equals(role)) {
            return ResponseEntity.badRequest().body(null);
        }
        User updatedUser = userService.updateUserRole(id, role);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();*/
        return ResponseEntity.ok(userService.updateUserRole(id, role));
    }
}
