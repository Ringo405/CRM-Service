package com.agilemonkeys.crm.api.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Surname {
    private final String value;

    public Surname(String value) {
        if (value == null || value.length() < 2 || value.length() > 50) {
            throw new IllegalArgumentException("Surname must be between 2 and 50 characters and cannot be empty");
        }
        this.value = value;
    }
}
