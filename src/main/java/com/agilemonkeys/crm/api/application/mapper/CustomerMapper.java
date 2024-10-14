package com.agilemonkeys.crm.api.application.mapper;

import com.agilemonkeys.crm.api.application.dto.customer.create.CreateCustomerCommand;
import com.agilemonkeys.crm.api.application.dto.customer.create.CreateCustomerResponse;
import com.agilemonkeys.crm.api.application.dto.customer.query.CustomerQueryResponse;
import com.agilemonkeys.crm.api.application.dto.customer.update.UpdateCustomerResponse;
import com.agilemonkeys.crm.api.domain.customer.Customer;
import com.agilemonkeys.crm.api.domain.valueobject.*;
import com.agilemonkeys.crm.api.infrastructure.persistance.entity.CustomerEntity;
import org.springframework.stereotype.Component;

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

    public CustomerQueryResponse toQueryResponse(Customer customer) {
        return CustomerQueryResponse.builder()
                .id(customer.getId().getValue())
                .name(customer.getName().getValue())
                .surname(customer.getSurname().getValue())
                .photoUrl(customer.getPhotoUrl().getValue())
                .createdBy(customer.getCreatedBy() != null ? customer.getCreatedBy().getValue() : null)
                .lastModifiedBy(customer.getLastModifiedBy() != null ? customer.getLastModifiedBy().getValue() : null)
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }

    public Customer toDomain(CreateCustomerCommand command) {
        return Customer.builder()
                .name(new Name(command.getName()))
                .surname(new Surname(command.getSurname()))
                .photoUrl(new PhotoUrl(command.getPhotoUrl()))
                .build();
    }

    public CreateCustomerResponse toCreateResponse(Customer customer) {
        return CreateCustomerResponse.builder()
                .id(customer.getId().getValue())
                .name(customer.getName().getValue())
                .surname(customer.getSurname().getValue())
                .photoUrl(customer.getPhotoUrl().getValue())
                .createdBy(customer.getCreatedBy() != null ? customer.getCreatedBy().getValue() : null)
                .lastModifiedBy(customer.getLastModifiedBy() != null ? customer.getLastModifiedBy().getValue() : null)
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }

    public UpdateCustomerResponse toUpdateResponse(Customer customer) {
        return UpdateCustomerResponse.builder()
                .id(customer.getId().getValue())
                .name(customer.getName().getValue())
                .surname(customer.getSurname().getValue())
                .photoUrl(customer.getPhotoUrl().getValue())
                .createdBy(customer.getCreatedBy() != null ? customer.getCreatedBy().getValue() : null)
                .lastModifiedBy(customer.getLastModifiedBy() != null ? customer.getLastModifiedBy().getValue() : null)
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }
}
