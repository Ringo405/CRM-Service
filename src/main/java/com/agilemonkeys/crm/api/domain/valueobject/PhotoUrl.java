package com.agilemonkeys.crm.api.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode
public class PhotoUrl {
    private final String value;

    private static final String URL_REGEX =
            "^(http://|https://)\\S+$";

    public PhotoUrl(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Photo URL cannot be null or empty");
        }
        if (!Pattern.matches(URL_REGEX, value)) {
            throw new IllegalArgumentException("Photo URL is not valid");
        }
        this.value = value;
    }
}
