package com.agilemonkeys.crm.api.application.mapper;

import com.agilemonkeys.crm.api.domain.customer.Customer;
import com.agilemonkeys.crm.api.domain.valueobject.*;
import com.agilemonkeys.crm.api.infrastructure.model.CustomerEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {

    public CustomerEntity toEntity(Customer customer) {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(customer.getId() != null ? customer.getId().getValue() : null);
        entity.setName(customer.getName().getValue());
        entity.setSurname(customer.getSurname().getValue());
        entity.setPhotoUrl(customer.getPhotoUrl().getValue());
        entity.setCreatedBy(customer.getCreatedBy() != null ? customer.getCreatedBy().getValue() : null);
        entity.setLastModifiedBy(customer.getLastModifiedBy() != null ? customer.getLastModifiedBy().getValue() : null);
        entity.setCreatedAt(customer.getCreatedAt());
        entity.setUpdatedAt(customer.getUpdatedAt());
        return entity;
    }

    // Mapea CustomerEntity a Customer
    public Customer toDomain(CustomerEntity entity) {
        return Customer.builder()
                .id(new CustomerId(entity.getId()))
                .name(new Name(entity.getName()))
                .surname(new Surname(entity.getSurname()))
                .photoUrl(new PhotoUrl(entity.getPhotoUrl()))
                .createdBy(entity.getCreatedBy() != null ? new UserId(entity.getCreatedBy()) : null)
                .lastModifiedBy(entity.getLastModifiedBy() != null ? new UserId(entity.getLastModifiedBy()) : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public List<Customer> toDomainList(List<CustomerEntity> entities) {
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

}
