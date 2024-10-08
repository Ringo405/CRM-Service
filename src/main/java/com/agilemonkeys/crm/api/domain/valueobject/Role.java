package com.agilemonkeys.crm.api.domain.valueobject;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("Administrator"),
    USER("User");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public static Role fromDescription(String description) {
        for (Role role : Role.values()) {
            if (role.getDescription().equalsIgnoreCase(description)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No Role with description " + description + " found");
    }
}
