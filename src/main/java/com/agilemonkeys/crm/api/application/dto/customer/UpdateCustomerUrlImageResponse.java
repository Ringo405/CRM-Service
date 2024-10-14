package com.agilemonkeys.crm.api.application.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class UpdateCustomerUrlImageResponse {
    private String url;
}
