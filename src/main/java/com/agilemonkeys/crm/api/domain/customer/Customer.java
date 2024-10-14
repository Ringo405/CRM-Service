package com.agilemonkeys.crm.api.domain.customer;

import com.agilemonkeys.crm.api.domain.common.AggregateRoot;
import com.agilemonkeys.crm.api.domain.valueobject.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class Customer extends AggregateRoot<CustomerId> {
    private Name name;
    private Surname surname;
    private PhotoUrl photoUrl;
    private UserId createdBy;
    private UserId lastModifiedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void validate() {
        if (name == null || name.getValue() == null || name.getValue().isEmpty()) {
            throw new IllegalArgumentException("Name is required.");
        }
        if (name.getValue().length() > 100) {
            throw new IllegalArgumentException("Name cannot exceed 100 characters.");
        }

        if (surname == null || surname.getValue() == null || surname.getValue().isEmpty()) {
            throw new IllegalArgumentException("Surname is required.");
        }
        if (surname.getValue().length() > 100) {
            throw new IllegalArgumentException("Surname cannot exceed 100 characters.");
        }

        if (photoUrl == null || photoUrl.getValue() == null || photoUrl.getValue().isEmpty()) {
            throw new IllegalArgumentException("Photo URL is required.");
        }
        if (!isValidUrl(photoUrl.getValue())) {
            throw new IllegalArgumentException("Photo URL is not valid.");
        }
    }

    private boolean isValidUrl(String url) {
        String regex = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$";
        return url.matches(regex);
    }

    public void initialize() {
        this.createdAt = LocalDateTime.now();
    }
}
