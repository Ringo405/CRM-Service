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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint to retrieve all users in the system.
     *
     * This endpoint allows an administrator to fetch a list of all users
     * currently registered in the system.
     *
     * @return ResponseEntity containing a list of users. The response includes
     *         a collection of user details.
     */
    @Operation(summary = "Retrieve all users",
            description = "Allows an administrator to fetch a list of all users registered in the system.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/vnd.api.v1+json"))
            })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsersQueryResponse> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Endpoint to retrieve a user by ID.
     *
     * This endpoint allows an administrator to fetch the details of a specific user
     * by their unique identifier.
     *
     * @param id The unique identifier of the user to retrieve. This parameter is mandatory.
     * @return ResponseEntity containing the user's details.
     */
    @Operation(summary = "Retrieve a user by ID",
            description = "Allows an administrator to fetch details of a specific user using their ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User retrieved successfully",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/vnd.api.v1+json"))
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserQueryResponse> getUserById(@PathVariable Long id) {
        UserQuery userQuery = UserQuery.builder().id(id).build();
        return ResponseEntity.ok(userService.getUserById(userQuery));
    }

    /**
     * Endpoint to create a new user.
     *
     * This endpoint allows an administrator to create a new user in the system.
     * The request body must contain the necessary information to create the user.
     *
     * @param command The command object containing the details of the user to create. This is mandatory.
     * @return ResponseEntity containing the details of the created user.
     */
    @Operation(summary = "Create a new user",
            description = "Allows an administrator to create a new user in the system.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created successfully",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "400", description = "Invalid input data",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/vnd.api.v1+json"))
            })
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserCommand command) {
        return ResponseEntity.ok(userService.createUser(command));
    }

    /**
     * Endpoint to update an existing user.
     *
     * This endpoint allows an administrator to update the details of an existing user
     * using their unique identifier.
     *
     * @param id The unique identifier of the user to update. This parameter is mandatory.
     * @param command The command object containing the updated details of the user. This is mandatory.
     * @return ResponseEntity containing the details of the updated user.
     */
    @Operation(summary = "Update an existing user",
            description = "Allows an administrator to update the details of an existing user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User updated successfully",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "400", description = "Invalid input data",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/vnd.api.v1+json"))
            })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UpdateUserResponse> updateUser(@PathVariable Long id, @RequestBody UpdateUserCommand command) {
        command.setId(id);
        return ResponseEntity.ok(userService.updateUser(command));
    }

    /**
     * Endpoint to delete a user by ID.
     *
     * This endpoint allows an administrator to delete a specific user from the system
     * using their unique identifier.
     *
     * @param id The unique identifier of the user to delete. This parameter is mandatory.
     * @return ResponseEntity with no content to indicate successful deletion.
     */
    @Operation(summary = "Delete a user by ID",
            description = "Allows an administrator to delete a user from the system using their ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/vnd.api.v1+json"))
            })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint to update a user's role.
     *
     * This endpoint allows an administrator to update the role of a specific user
     * using their unique identifier.
     *
     * @param id The unique identifier of the user whose role is to be updated. This parameter is mandatory.
     * @param command The command object containing the new role details. This is mandatory.
     * @return ResponseEntity containing the details of the updated user.
     */
    @Operation(summary = "Update a user's role",
            description = "Allows an administrator to update the role of a specific user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User role updated successfully",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "400", description = "Invalid input data",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/vnd.api.v1+json"))
            })
    @PatchMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UpdateUserResponse> updateUserRole(@PathVariable Long id, @RequestBody UpdateUserRoleCommand command) {
        command.setId(id);
        return ResponseEntity.ok(userService.updateUserRole(command));
    }
}
