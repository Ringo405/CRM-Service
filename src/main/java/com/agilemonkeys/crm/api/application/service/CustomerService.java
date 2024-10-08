package com.agilemonkeys.crm.api.application.service;

import com.agilemonkeys.crm.api.application.dto.customer.query.CustomerQuery;
import com.agilemonkeys.crm.api.application.dto.customer.query.CustomerQueryResponse;
import com.agilemonkeys.crm.api.application.dto.customer.query.CustomersQueryResponse;
import com.agilemonkeys.crm.api.domain.customer.Customer;


public interface CustomerService {
    CustomersQueryResponse getAllCustomers();

    CustomerQueryResponse getCustomerById(CustomerQuery customerQuery);

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Long id, Customer customerDetails);

    void deleteCustomer(Long id);
}
