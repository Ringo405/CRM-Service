package com.agilemonkeys.crm.api.application.dto.user;

import com.agilemonkeys.crm.api.domain.valueobject.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserResponse {
    private Long id;
    private String username;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
