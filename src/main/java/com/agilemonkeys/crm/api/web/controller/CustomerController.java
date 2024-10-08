package com.agilemonkeys.crm.api.web.controller;

import com.agilemonkeys.crm.api.application.dto.customer.query.CustomerQuery;
import com.agilemonkeys.crm.api.application.dto.customer.query.CustomerQueryResponse;
import com.agilemonkeys.crm.api.application.dto.customer.query.CustomersQueryResponse;
import com.agilemonkeys.crm.api.domain.customer.Customer;
import com.agilemonkeys.crm.api.application.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<CustomersQueryResponse> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerQueryResponse> getCustomerById(@PathVariable Long id) {
        CustomerQuery customerQuery = CustomerQuery.builder().id(id).build();
        return ResponseEntity.ok(customerService.getCustomerById(customerQuery));
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(customer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customerDetails) {
        return ResponseEntity.ok(customerService.updateCustomer(id, customerDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
