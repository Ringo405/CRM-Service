package com.agilemonkeys.crm.api.web.controller.customer;

import com.agilemonkeys.crm.api.application.dto.customer.create.CreateCustomerCommand;
import com.agilemonkeys.crm.api.application.dto.customer.create.CreateCustomerResponse;
import com.agilemonkeys.crm.api.application.dto.customer.query.CustomerQuery;
import com.agilemonkeys.crm.api.application.dto.customer.query.CustomerQueryResponse;
import com.agilemonkeys.crm.api.application.dto.customer.query.CustomersQueryResponse;
import com.agilemonkeys.crm.api.application.dto.customer.update.UpdateCustomerCommand;
import com.agilemonkeys.crm.api.application.dto.customer.update.UpdateCustomerResponse;
import com.agilemonkeys.crm.api.application.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCustomers() {
        CustomersQueryResponse customersQueryResponse = new CustomersQueryResponse(Collections.emptyList());
        when(customerService.getAllCustomers()).thenReturn(customersQueryResponse);

        ResponseEntity<CustomersQueryResponse> response = customerController.getAllCustomers();

        assertEquals(customersQueryResponse, response.getBody());
        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    void testGetCustomerById() {
        Long customerId = 1L;
        CustomerQueryResponse customerQueryResponse = new CustomerQueryResponse();
        when(customerService.getCustomerById(any(CustomerQuery.class))).thenReturn(customerQueryResponse);

        ResponseEntity<CustomerQueryResponse> response = customerController.getCustomerById(customerId);

        assertEquals(customerQueryResponse, response.getBody());
        ArgumentCaptor<CustomerQuery> captor = ArgumentCaptor.forClass(CustomerQuery.class);
        verify(customerService, times(1)).getCustomerById(captor.capture());
        assertEquals(customerId, captor.getValue().getId());
    }

    @Test
    void testCreateCustomer() {
        CreateCustomerCommand command = new CreateCustomerCommand();
        CreateCustomerResponse createCustomerResponse = new CreateCustomerResponse();
        when(customerService.createCustomer(command)).thenReturn(createCustomerResponse);

        ResponseEntity<CreateCustomerResponse> response = customerController.createCustomer(command);

        assertEquals(createCustomerResponse, response.getBody());
        verify(customerService, times(1)).createCustomer(command);
    }

    @Test
    void testUpdateCustomer() {
        Long customerId = 1L;
        UpdateCustomerCommand command = new UpdateCustomerCommand();
        UpdateCustomerResponse updateCustomerResponse = new UpdateCustomerResponse();
        when(customerService.updateCustomer(command)).thenReturn(updateCustomerResponse);

        ResponseEntity<UpdateCustomerResponse> response = customerController.updateCustomer(customerId, command);

        assertEquals(updateCustomerResponse, response.getBody());
        assertEquals(customerId, command.getId());
        verify(customerService, times(1)).updateCustomer(command);
    }

    @Test
    void testDeleteCustomer() {
        Long customerId = 1L;

        customerController.deleteCustomer(customerId);

        verify(customerService, times(1)).deleteCustomer(customerId);
    }
}
