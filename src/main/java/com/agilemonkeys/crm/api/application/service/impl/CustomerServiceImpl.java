package com.agilemonkeys.crm.api.application.service.impl;

import com.agilemonkeys.crm.api.application.mapper.CustomerMapper;
import com.agilemonkeys.crm.api.application.service.CustomerService;
import com.agilemonkeys.crm.api.domain.customer.Customer;
import com.agilemonkeys.crm.api.infrastructure.exception.NotFoundException;
import com.agilemonkeys.crm.api.infrastructure.model.CustomerEntity;
import com.agilemonkeys.crm.api.infrastructure.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<CustomerEntity> customerEntities = customerRepository.findAll();
        return customerMapper.toDomainList(customerEntities);
    }

    @Override
    public Customer getCustomerById(Long id) {
        CustomerEntity entity = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));
        return customerMapper.toDomain(entity);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        CustomerEntity customerEntity = customerMapper.toEntity(customer);
        CustomerEntity savedEntity = customerRepository.save(customerEntity);
        return customerMapper.toDomain(savedEntity);
    }

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
