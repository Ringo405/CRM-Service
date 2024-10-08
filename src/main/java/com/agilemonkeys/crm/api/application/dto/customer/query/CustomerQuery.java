package com.agilemonkeys.crm.api.application.dto.customer.query;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CustomerQuery {
    @NotNull
    private Long id;
}
