package com.agilemonkeys.crm.api.domain.user;

import com.agilemonkeys.crm.api.domain.common.AggregateRoot;
import com.agilemonkeys.crm.api.domain.valueobject.Password;
import com.agilemonkeys.crm.api.domain.valueobject.Role;
import com.agilemonkeys.crm.api.domain.valueobject.UserId;
import com.agilemonkeys.crm.api.domain.valueobject.Username;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class User extends AggregateRoot<UserId> {
    private Username username;
    private Password password;
    private Role role;
    private Long createdBy;
    private Long lastModifiedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void initialize() {
        this.createdAt = LocalDateTime.now();
    }

    public void validate() {
        if (username == null || username.getValue() == null || username.getValue().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        if (password == null || password.getValue() == null || password.getValue().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }

        if (username.getValue().length() < 4 || username.getValue().length() > 50) {
            throw new IllegalArgumentException("Username must be between 4 and 50 characters long");
        }

        if (password.getValue().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }

        if (!username.getValue().matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException("Username can only contain letters, numbers, and underscores");
        }

        if (!isPasswordComplex(password.getValue())) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character");
        }
    }

    private boolean isPasswordComplex(String password) {
        boolean hasUpperCase = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLowerCase = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecialChar = password.chars().anyMatch(ch -> "!@#$%^&*()_+".indexOf(ch) >= 0);

        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
    }
}
