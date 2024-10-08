package com.agilemonkeys.crm.api.application.dto.customer.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CustomersQueryResponse {
    List<CustomerQueryResponse> customers;
}
