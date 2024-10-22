package com.agilemonkeys.crm.api.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class PhotoUrl {
    private final String value;

    public PhotoUrl(String value) {
        this.value = value;
    }
}
