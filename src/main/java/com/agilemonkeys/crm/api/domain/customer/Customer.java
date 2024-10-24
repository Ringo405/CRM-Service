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
    }

    public void initialize() {
        this.createdAt = LocalDateTime.now();
    }
}
