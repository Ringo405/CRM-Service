package com.agilemonkeys.crm.api.application.service;

import com.agilemonkeys.crm.api.application.dto.customer.create.CreateCustomerCommand;
import com.agilemonkeys.crm.api.application.dto.customer.create.CreateCustomerResponse;
import com.agilemonkeys.crm.api.application.dto.customer.query.CustomerQuery;
import com.agilemonkeys.crm.api.application.dto.customer.query.CustomerQueryResponse;
import com.agilemonkeys.crm.api.application.dto.customer.query.CustomersQueryResponse;
import com.agilemonkeys.crm.api.application.dto.customer.update.UpdateCustomerCommand;
import com.agilemonkeys.crm.api.application.dto.customer.update.UpdateCustomerResponse;


public interface CustomerService {
    CustomersQueryResponse getAllCustomers();

    CustomerQueryResponse getCustomerById(CustomerQuery customerQuery);

    CreateCustomerResponse createCustomer(CreateCustomerCommand command);

    UpdateCustomerResponse updateCustomer(UpdateCustomerCommand command);

    void deleteCustomer(Long id);
}
