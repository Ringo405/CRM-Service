package com.agilemonkeys.crm.api.application.service;

import com.agilemonkeys.crm.api.application.dto.customer.query.CustomerQuery;
import com.agilemonkeys.crm.api.application.dto.customer.query.CustomerQueryResponse;
import com.agilemonkeys.crm.api.application.dto.customer.query.CustomersQueryResponse;
import com.agilemonkeys.crm.api.application.dto.customer.update.UpdateCustomerCommand;
import com.agilemonkeys.crm.api.application.dto.customer.update.UpdateCustomerResponse;
import com.agilemonkeys.crm.api.application.mapper.CustomerMapper;
import com.agilemonkeys.crm.api.application.service.impl.CustomerServiceImpl;
import com.agilemonkeys.crm.api.domain.customer.Customer;
import com.agilemonkeys.crm.api.domain.valueobject.Name;
import com.agilemonkeys.crm.api.domain.valueobject.PhotoUrl;
import com.agilemonkeys.crm.api.domain.valueobject.Surname;
import com.agilemonkeys.crm.api.domain.valueobject.UserId;
import com.agilemonkeys.crm.api.infrastructure.exception.NotFoundException;
import com.agilemonkeys.crm.api.infrastructure.persistance.entity.CustomerEntity;
import com.agilemonkeys.crm.api.infrastructure.persistance.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static com.agilemonkeys.crm.api.infrastructure.exception.ErrorMessages.CUSTOMER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    public static Customer createTestCustomer() {
        return Customer.builder()
                .name(new Name("Pepe"))
                .surname(new Surname("Perez"))
                .photoUrl(new PhotoUrl("http://example.com/photo.jpg"))
                .createdBy(new UserId(1L))
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testGetAllCustomers() {
        CustomerEntity mockEntity = new CustomerEntity();
        mockEntity.setId(1L);
        mockEntity.setName("Pepe");
        mockEntity.setSurname("Perez");
        mockEntity.setPhotoUrl("http://example.com/photo.jpg");

        when(customerRepository.findAll()).thenReturn(Collections.singletonList(mockEntity));

        CustomerQueryResponse mockResponse = new CustomerQueryResponse();
        when(customerMapper.toDomain(mockEntity)).thenReturn(createTestCustomer());
        when(customerMapper.toQueryResponse(any(Customer.class))).thenReturn(mockResponse);

        CustomersQueryResponse response = customerService.getAllCustomers();

        assertNotNull(response);
        assertEquals(1, response.getCustomers().size());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void testGetCustomerById_Success() {
        Long customerId = 1L;
        CustomerEntity mockEntity = new CustomerEntity();
        mockEntity.setId(customerId);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(mockEntity));

        Customer mockCustomer = createTestCustomer();
        when(customerMapper.toDomain(mockEntity)).thenReturn(mockCustomer);
        CustomerQueryResponse mockResponse = new CustomerQueryResponse();
        when(customerMapper.toQueryResponse(mockCustomer)).thenReturn(mockResponse);

        CustomerQueryResponse response = customerService.getCustomerById(new CustomerQuery(customerId));

        assertNotNull(response);
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    void testGetCustomerById_NotFound() {
        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            customerService.getCustomerById(new CustomerQuery(customerId));
        });

        assertEquals(CUSTOMER_NOT_FOUND, thrown.getMessage());
        verify(customerRepository, times(1)).findById(customerId);
    }

  /*  @Test
    void testCreateCustomer() {
        CreateCustomerCommand command = new CreateCustomerCommand();
        command.setName("Pepe");
        command.setSurname("Perez");
        command.setPhotoUrl("http://example.com/photo.jpg");

        Customer mockCustomer = createTestCustomer();
        when(customerMapper.toDomain(command)).thenReturn(mockCustomer);

        CustomerEntity mockEntity = new CustomerEntity();
        when(customerMapper.toEntity(mockCustomer)).thenReturn(mockEntity);
        when(customerRepository.save(mockEntity)).thenReturn(mockEntity);

        CreateCustomerResponse response = customerService.createCustomer(command);

        assertNotNull(response);
        verify(customerRepository, times(1)).save(mockEntity);
    }*/

    @Test
    void testUpdateCustomer_Success() {
        Long customerId = 1L;
        UpdateCustomerCommand command = new UpdateCustomerCommand();
        command.setId(customerId);
        command.setName("Updated Name");

        CustomerEntity existingEntity = new CustomerEntity();
        existingEntity.setId(customerId);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingEntity));

        when(customerRepository.save(existingEntity)).thenReturn(existingEntity);
        Customer mockCustomer = createTestCustomer();
        when(customerMapper.toDomain(existingEntity)).thenReturn(mockCustomer);
        UpdateCustomerResponse mockResponse = new UpdateCustomerResponse();
        when(customerMapper.toUpdateResponse(mockCustomer)).thenReturn(mockResponse);

        UpdateCustomerResponse response = customerService.updateCustomer(command);

        assertNotNull(response);
        assertEquals("Updated Name", existingEntity.getName());
        verify(customerRepository, times(1)).save(existingEntity);
    }

    @Test
    void testUpdateCustomer_NotFound() {
        Long customerId = 1L;
        UpdateCustomerCommand command = new UpdateCustomerCommand();
        command.setId(customerId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            customerService.updateCustomer(command);
        });

        assertEquals(CUSTOMER_NOT_FOUND, thrown.getMessage());
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    void testDeleteCustomer_Success() {
        Long customerId = 1L;
        when(customerRepository.existsById(customerId)).thenReturn(true);

        customerService.deleteCustomer(customerId);

        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    void testDeleteCustomer_NotFound() {
        Long customerId = 1L;
        when(customerRepository.existsById(customerId)).thenReturn(false);

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            customerService.deleteCustomer(customerId);
        });

        assertEquals(CUSTOMER_NOT_FOUND, thrown.getMessage());
        verify(customerRepository, times(1)).existsById(customerId);
    }
}
