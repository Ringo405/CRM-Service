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
}
