package com.cafe.storeservice.repository;

import com.cafe.storeservice.domain.InventoryLog;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryLogRepository extends JpaRepository<InventoryLog, Log> {
    Page<InventoryLog> findAll(Specification<InventoryLog> search, Pageable pageable);
}
