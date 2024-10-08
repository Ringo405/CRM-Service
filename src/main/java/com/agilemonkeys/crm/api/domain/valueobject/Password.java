package com.agilemonkeys.crm.api.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Password {
    private final String value;

    public Password(String value) {
        if (value == null || value.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }
        this.value = value;
    }
}
