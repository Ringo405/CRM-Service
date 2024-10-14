package com.agilemonkeys.crm.api.web.controller.customer;

import com.agilemonkeys.crm.api.application.dto.customer.create.CreateCustomerCommand;
import com.agilemonkeys.crm.api.application.dto.customer.create.CreateCustomerResponse;
import com.agilemonkeys.crm.api.application.dto.customer.query.CustomerQuery;
import com.agilemonkeys.crm.api.application.dto.customer.query.CustomerQueryResponse;
import com.agilemonkeys.crm.api.application.dto.customer.query.CustomersQueryResponse;
import com.agilemonkeys.crm.api.application.dto.customer.update.UpdateCustomerCommand;
import com.agilemonkeys.crm.api.application.dto.customer.update.UpdateCustomerResponse;
import com.agilemonkeys.crm.api.application.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CustomersQueryResponse> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CustomerQueryResponse> getCustomerById(@PathVariable Long id) {
        CustomerQuery customerQuery = CustomerQuery.builder().id(id).build();
        return ResponseEntity.ok(customerService.getCustomerById(customerQuery));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateCustomerResponse> createCustomer(@RequestBody CreateCustomerCommand command) {
        return ResponseEntity.ok(customerService.createCustomer(command));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UpdateCustomerResponse> updateCustomer(@PathVariable Long id, @RequestBody UpdateCustomerCommand command) {
        command.setId(id);
        return ResponseEntity.ok(customerService.updateCustomer(command));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
