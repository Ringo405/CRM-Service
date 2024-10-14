package com.agilemonkeys.crm.api.application.service.impl;

import com.agilemonkeys.crm.api.application.dto.customer.create.CreateCustomerCommand;
import com.agilemonkeys.crm.api.application.dto.customer.create.CreateCustomerResponse;
import com.agilemonkeys.crm.api.application.dto.customer.query.CustomerQuery;
import com.agilemonkeys.crm.api.application.dto.customer.query.CustomerQueryResponse;
import com.agilemonkeys.crm.api.application.dto.customer.query.CustomersQueryResponse;
import com.agilemonkeys.crm.api.application.dto.customer.update.UpdateCustomerCommand;
import com.agilemonkeys.crm.api.application.dto.customer.update.UpdateCustomerResponse;
import com.agilemonkeys.crm.api.application.mapper.CustomerMapper;
import com.agilemonkeys.crm.api.application.service.CustomerService;
import com.agilemonkeys.crm.api.domain.customer.Customer;
import com.agilemonkeys.crm.api.infrastructure.exception.NotFoundException;
import com.agilemonkeys.crm.api.infrastructure.persistance.entity.CustomerEntity;
import com.agilemonkeys.crm.api.infrastructure.persistance.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.agilemonkeys.crm.api.infrastructure.exception.ErrorMessages.CUSTOMER_NOT_FOUND;

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
                .orElseThrow(() -> new NotFoundException(CUSTOMER_NOT_FOUND));
        Customer savedCustomer = customerMapper.toDomain(entity);
        return customerMapper.toQueryResponse(savedCustomer);
    }

    @Override
    public CreateCustomerResponse createCustomer(CreateCustomerCommand command) {
        Customer customer = customerMapper.toDomain(command);

        customer.initialize();
        customer.validate();

        CustomerEntity customerEntity = customerMapper.toEntity(customer);
        CustomerEntity savedEntity = customerRepository.save(customerEntity);
        return customerMapper.toCreateResponse(customerMapper.toDomain(savedEntity));
    }

    @Override
    public UpdateCustomerResponse updateCustomer(UpdateCustomerCommand command) {
        CustomerEntity existingEntity = customerRepository.findById(command.getId())
                .orElseThrow(() -> new NotFoundException(CUSTOMER_NOT_FOUND));

        if (command.getName() != null && !command.getName().isEmpty()) {
            existingEntity.setName(command.getName());
        }

        if (command.getSurname() != null && !command.getSurname().isEmpty()) {
            existingEntity.setSurname(command.getSurname());
        }

        if (command.getPhotoUrl() != null && !command.getPhotoUrl().isEmpty()) {
            existingEntity.setPhotoUrl(command.getPhotoUrl());
        }

        CustomerEntity savedEntity = customerRepository.save(existingEntity);
        Customer savedCustomerDomain = customerMapper.toDomain(savedEntity);
        return customerMapper.toUpdateResponse(savedCustomerDomain);
    }

    @Override
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new NotFoundException(CUSTOMER_NOT_FOUND);
        }
        customerRepository.deleteById(id);
    }

    @Override
    public void updateCustomerPhotoUrl(Long customerId, String photoUrl) {
        CustomerEntity customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + customerId));

        customer.setPhotoUrl(photoUrl);
        customerRepository.save(customer);
    }
}
