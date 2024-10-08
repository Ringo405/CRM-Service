package com.agilemonkeys.crm.api.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class UserId {
    private final Long value;

    public UserId(Long value) {
        this.value = value;
    }
}
