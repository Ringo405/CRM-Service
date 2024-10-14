package com.agilemonkeys.crm.api.application.mapper;

import com.agilemonkeys.crm.api.application.dto.customer.create.CreateCustomerCommand;
import com.agilemonkeys.crm.api.application.dto.customer.create.CreateCustomerResponse;
import com.agilemonkeys.crm.api.application.dto.customer.query.CustomerQueryResponse;
import com.agilemonkeys.crm.api.application.dto.customer.update.UpdateCustomerResponse;
import com.agilemonkeys.crm.api.domain.customer.Customer;
import com.agilemonkeys.crm.api.domain.valueobject.CustomerId;
import com.agilemonkeys.crm.api.domain.valueobject.Name;
import com.agilemonkeys.crm.api.domain.valueobject.PhotoUrl;
import com.agilemonkeys.crm.api.domain.valueobject.Surname;
import com.agilemonkeys.crm.api.infrastructure.persistance.entity.CustomerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerMapperTest {

    private CustomerMapper customerMapper;

    @BeforeEach
    public void setUp() {
        customerMapper = new CustomerMapper();
    }

    @Test
    void testToEntity() {
        Customer mockCustomer = mock(Customer.class);

        when(mockCustomer.getId()).thenReturn(null);
        when(mockCustomer.getName()).thenReturn(new Name("Pepe"));
        when(mockCustomer.getSurname()).thenReturn(new Surname("Martin"));
        when(mockCustomer.getPhotoUrl()).thenReturn(new PhotoUrl("http://photo.url"));
        when(mockCustomer.getCreatedBy()).thenReturn(null);
        when(mockCustomer.getLastModifiedBy()).thenReturn(null);
        when(mockCustomer.getCreatedAt()).thenReturn(null);
        when(mockCustomer.getUpdatedAt()).thenReturn(null);

        CustomerEntity result = customerMapper.toEntity(mockCustomer);

        assertNull(result.getId());
        assertEquals("Pepe", result.getName());
        assertEquals("Martin", result.getSurname());
        assertEquals("http://photo.url", result.getPhotoUrl());
        assertNull(result.getCreatedBy());
        assertNull(result.getLastModifiedBy());
        assertNull(result.getCreatedAt());
        assertNull(result.getUpdatedAt());
    }

    @Test
    void testToDomain() {
        CustomerEntity mockEntity = new CustomerEntity();
        mockEntity.setId(1L);
        mockEntity.setName("Pepe");
        mockEntity.setSurname("Martin");
        mockEntity.setPhotoUrl("http://photo.url");
        mockEntity.setCreatedBy(null);
        mockEntity.setLastModifiedBy(null);
        mockEntity.setCreatedAt(null);
        mockEntity.setUpdatedAt(null);

        Customer result = customerMapper.toDomain(mockEntity);

        assertEquals(new CustomerId(1L), result.getId());
        assertEquals(new Name("Pepe"), result.getName());
        assertEquals(new Surname("Martin"), result.getSurname());
        assertEquals(new PhotoUrl("http://photo.url"), result.getPhotoUrl());
        assertNull(result.getCreatedBy());
        assertNull(result.getLastModifiedBy());
        assertNull(result.getCreatedAt());
        assertNull(result.getUpdatedAt());
    }

    @Test
    void testToQueryResponse() {
        Customer mockCustomer = mock(Customer.class);

        when(mockCustomer.getId()).thenReturn(new CustomerId(1L));
        when(mockCustomer.getName()).thenReturn(new Name("Pepe"));
        when(mockCustomer.getSurname()).thenReturn(new Surname("Martin"));
        when(mockCustomer.getPhotoUrl()).thenReturn(new PhotoUrl("http://photo.url"));
        when(mockCustomer.getCreatedBy()).thenReturn(null);
        when(mockCustomer.getLastModifiedBy()).thenReturn(null);
        when(mockCustomer.getCreatedAt()).thenReturn(null);
        when(mockCustomer.getUpdatedAt()).thenReturn(null);

        CustomerQueryResponse result = customerMapper.toQueryResponse(mockCustomer);

        assertEquals(1L, result.getId());
        assertEquals("Pepe", result.getName());
        assertEquals("Martin", result.getSurname());
        assertEquals("http://photo.url", result.getPhotoUrl());
        assertNull(result.getCreatedBy());
        assertNull(result.getLastModifiedBy());
        assertNull(result.getCreatedAt());
        assertNull(result.getUpdatedAt());
    }

    @Test
    void testToDomainWithCommand() {
        CreateCustomerCommand mockCommand = mock(CreateCustomerCommand.class);

        when(mockCommand.getName()).thenReturn("Pepe");
        when(mockCommand.getSurname()).thenReturn("Martin");
        when(mockCommand.getPhotoUrl()).thenReturn("http://photo.url");

        Customer result = customerMapper.toDomain(mockCommand);

        assertEquals(new Name("Pepe"), result.getName());
        assertEquals(new Surname("Martin"), result.getSurname());
        assertEquals(new PhotoUrl("http://photo.url"), result.getPhotoUrl());
    }

    @Test
    void testToCreateResponse() {
        Customer mockCustomer = mock(Customer.class);

        when(mockCustomer.getId()).thenReturn(new CustomerId(1L));
        when(mockCustomer.getName()).thenReturn(new Name("Pepe"));
        when(mockCustomer.getSurname()).thenReturn(new Surname("Martin"));
        when(mockCustomer.getPhotoUrl()).thenReturn(new PhotoUrl("http://photo.url"));
        when(mockCustomer.getCreatedBy()).thenReturn(null);
        when(mockCustomer.getLastModifiedBy()).thenReturn(null);
        when(mockCustomer.getCreatedAt()).thenReturn(null);
        when(mockCustomer.getUpdatedAt()).thenReturn(null);

        CreateCustomerResponse result = customerMapper.toCreateResponse(mockCustomer);

        assertEquals(1L, result.getId());
        assertEquals("Pepe", result.getName());
        assertEquals("Martin", result.getSurname());
        assertEquals("http://photo.url", result.getPhotoUrl());
        assertNull(result.getCreatedBy());
        assertNull(result.getLastModifiedBy());
        assertNull(result.getCreatedAt());
        assertNull(result.getUpdatedAt());
    }

    @Test
    void testToUpdateResponse() {
        Customer mockCustomer = mock(Customer.class);

        when(mockCustomer.getId()).thenReturn(new CustomerId(1L));
        when(mockCustomer.getName()).thenReturn(new Name("Pepe"));
        when(mockCustomer.getSurname()).thenReturn(new Surname("Martin"));
        when(mockCustomer.getPhotoUrl()).thenReturn(new PhotoUrl("http://photo.url"));
        when(mockCustomer.getCreatedBy()).thenReturn(null);
        when(mockCustomer.getLastModifiedBy()).thenReturn(null);
        when(mockCustomer.getCreatedAt()).thenReturn(null);
        when(mockCustomer.getUpdatedAt()).thenReturn(null);

        UpdateCustomerResponse result = customerMapper.toUpdateResponse(mockCustomer);

        assertEquals(1L, result.getId());
        assertEquals("Pepe", result.getName());
        assertEquals("Martin", result.getSurname());
        assertEquals("http://photo.url", result.getPhotoUrl());
        assertNull(result.getCreatedBy());
        assertNull(result.getLastModifiedBy());
        assertNull(result.getCreatedAt());
        assertNull(result.getUpdatedAt());
    }
}
