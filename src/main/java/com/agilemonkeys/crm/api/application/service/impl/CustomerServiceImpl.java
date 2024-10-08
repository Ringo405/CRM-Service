package com.agilemonkeys.crm.api.application.service.impl;

import com.agilemonkeys.crm.api.application.dto.customer.query.CustomerQuery;
import com.agilemonkeys.crm.api.application.dto.customer.query.CustomerQueryResponse;
import com.agilemonkeys.crm.api.application.dto.customer.query.CustomersQueryResponse;
import com.agilemonkeys.crm.api.application.mapper.CustomerMapper;
import com.agilemonkeys.crm.api.application.service.CustomerService;
import com.agilemonkeys.crm.api.domain.customer.Customer;
import com.agilemonkeys.crm.api.infrastructure.exception.NotFoundException;
import com.agilemonkeys.crm.api.infrastructure.model.CustomerEntity;
import com.agilemonkeys.crm.api.infrastructure.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public CustomersQueryResponse getAllCustomers() {
        List<CustomerEntity> customerEntities = customerRepository.findAll();
        List<CustomerQueryResponse> customerResponses = customerEntities.stream()
                .map(customerMapper::toDomain)
                .map(customerMapper::toQueryResponse)
                .collect(Collectors.toList());

        return new CustomersQueryResponse(customerResponses);
    }

    @Override
    public CustomerQueryResponse getCustomerById(CustomerQuery customerQuery) {
        CustomerEntity entity = customerRepository.findById(customerQuery.getId())
                .orElseThrow(() -> new NotFoundException("Customer not found"));
        Customer savedCustomer = customerMapper.toDomain(entity);
        return customerMapper.toQueryResponse(savedCustomer);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        CustomerEntity customerEntity = customerMapper.toEntity(customer);
        CustomerEntity savedEntity = customerRepository.save(customerEntity);
        return customerMapper.toDomain(savedEntity);
    }

   /* public CreateCustomerResponse createCustomer(CreateCustomerCommand command) {
        Customer customer = Customer.builder()
                .name(new Name(command.getName()))
                .surname(new Surname(command.getSurname()))
                .photoUrl(new PhotoUrl(command.getPhotoUrl()))
                .build();

        customer.initialize(command.getCreatedBy()); // Inicializa con el creador
        customer.validate(); // Realiza validaciones

        CustomerEntity customerEntity = customerMapper.toEntity(customer);
        CustomerEntity savedEntity = customerRepository.save(customerEntity);

        return customerMapper.toResponse(savedEntity);
    }*/

    @Override
    public Customer updateCustomer(Long id, Customer customerDetails) {
        CustomerEntity customerEntity = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        customerEntity.setName(customerDetails.getName().getValue());
        customerEntity.setSurname(customerDetails.getSurname().getValue());
        customerEntity.setPhotoUrl(customerDetails.getPhotoUrl().getValue());

        CustomerEntity updatedEntity = customerRepository.save(customerEntity);
        return customerMapper.toDomain(updatedEntity);
    }

    @Override
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new NotFoundException("Customer not found");
        }
        customerRepository.deleteById(id);
    }
}
