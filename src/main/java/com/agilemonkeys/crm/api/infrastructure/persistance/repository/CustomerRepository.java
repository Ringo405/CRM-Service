package com.agilemonkeys.crm.api.infrastructure.persistance.repository;

import com.agilemonkeys.crm.api.infrastructure.persistance.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
