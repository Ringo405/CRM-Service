package com.agilemonkeys.crm.api.application.service;

import com.agilemonkeys.crm.api.domain.customer.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();

    Customer getCustomerById(Long id);

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Long id, Customer customerDetails);

    void deleteCustomer(Long id);
}
