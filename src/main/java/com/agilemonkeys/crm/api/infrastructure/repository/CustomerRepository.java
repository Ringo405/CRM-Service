package com.agilemonkeys.crm.api.infrastructure.repository;

import com.agilemonkeys.crm.api.infrastructure.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
