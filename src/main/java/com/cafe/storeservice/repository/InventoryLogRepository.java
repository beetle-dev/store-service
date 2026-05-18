package com.cafe.storeservice.repository;

import com.cafe.storeservice.domain.inventory.InventoryLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryLogRepository extends JpaRepository<InventoryLog, Long> {
    Page<InventoryLog> findAll(Specification<InventoryLog> search, Pageable pageable);
}
