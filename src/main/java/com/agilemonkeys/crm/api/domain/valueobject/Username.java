package com.agilemonkeys.crm.api.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Username {
    private final String value;

    public Username(String value) {
        if (value == null || value.length() < 4 || value.length() > 50) {
            throw new IllegalArgumentException("Username must be between 4 and 50 characters and cannot be empty");
        }
        this.value = value;
    }
}
