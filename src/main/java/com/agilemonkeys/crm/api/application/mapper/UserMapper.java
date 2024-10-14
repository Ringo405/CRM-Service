package com.agilemonkeys.crm.api.application.mapper;

import com.agilemonkeys.crm.api.application.dto.user.create.CreateUserCommand;
import com.agilemonkeys.crm.api.application.dto.user.create.CreateUserResponse;
import com.agilemonkeys.crm.api.application.dto.user.query.UserQueryResponse;
import com.agilemonkeys.crm.api.application.dto.user.update.UpdateUserResponse;
import com.agilemonkeys.crm.api.domain.user.User;
import com.agilemonkeys.crm.api.domain.valueobject.Password;
import com.agilemonkeys.crm.api.domain.valueobject.Role;
import com.agilemonkeys.crm.api.domain.valueobject.UserId;
import com.agilemonkeys.crm.api.domain.valueobject.Username;
import com.agilemonkeys.crm.api.infrastructure.persistance.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId() != null ? user.getId().getValue() : null);
        entity.setUsername(user.getUsername().getValue());
        entity.setPassword(user.getPassword().getValue());
        entity.setRole(user.getRole().name());
        entity.setCreatedBy(user.getCreatedBy() != null ? user.getCreatedBy() : null);
        entity.setLastModifiedBy(user.getLastModifiedBy() != null ? user.getLastModifiedBy() : null);
        entity.setCreatedAt(user.getCreatedAt());
        entity.setUpdatedAt(user.getUpdatedAt());

        return entity;
    }

    public User toDomain(UserEntity entity) {
        return User.builder()
                .id(new UserId(entity.getId()))
                .username(new Username(entity.getUsername()))
                .password(new Password(entity.getPassword()))
                .role(Role.fromString(entity.getRole()))
                .createdBy(entity.getCreatedBy() != null ? entity.getCreatedBy() : null)
                .lastModifiedBy(entity.getLastModifiedBy() != null ? entity.getLastModifiedBy() : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public UpdateUserResponse toUpdateResponse(User user) {
        return UpdateUserResponse.builder()
                .id(user.getId().getValue())
                .username(user.getUsername().getValue())
                .role(user.getRole())
                .createdBy(user.getCreatedBy() != null ? user.getCreatedBy() : null)
                .lastModifiedBy(user.getLastModifiedBy() != null ? user.getLastModifiedBy() : null)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public CreateUserResponse toCreateResponse(User user) {
        return CreateUserResponse.builder()
                .id(user.getId().getValue())
                .username(user.getUsername().getValue())
                .role(user.getRole())
                .createdBy(user.getCreatedBy() != null ? user.getCreatedBy() : null)
                .lastModifiedBy(user.getLastModifiedBy() != null ? user.getLastModifiedBy() : null)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public UserQueryResponse toQueryResponse(User user) {
        return UserQueryResponse.builder()
                .id(user.getId().getValue())
                .username(user.getUsername().getValue())
                .role(user.getRole())
                .createdBy(user.getCreatedBy() != null ? user.getCreatedBy() : null)
                .lastModifiedBy(user.getLastModifiedBy() != null ? user.getLastModifiedBy() : null)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public User toDomain(CreateUserCommand command) {
        return User.builder()
                .username(new Username(command.getUsername()))
                .password(new Password(command.getPassword()))
                .role(command.getRole())
                .build();
    }
}
