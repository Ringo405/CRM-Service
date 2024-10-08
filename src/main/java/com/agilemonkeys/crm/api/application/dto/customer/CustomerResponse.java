package com.agilemonkeys.crm.api.application.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CustomerResponse {
    private Long id;
    private String name;
    private String surname;
    private String photoUrl;
    private Long createdBy;
    private Long lastModifiedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
