package com.agilemonkeys.crm.api.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class CustomerId {
    private final Long value;

    public CustomerId(Long value) {
        this.value = value;
    }
}
