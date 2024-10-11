package com.agilemonkeys.crm.api.domain.valueobject;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN,
    USER;

    public static Role fromString(String role) {
        return Role.valueOf(role.toUpperCase());
    }
}
