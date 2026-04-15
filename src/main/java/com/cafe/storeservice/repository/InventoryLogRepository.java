package com.cafe.storeservice.repository;

import com.cafe.storeservice.domain.InventoryLog;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryLogRepository extends JpaRepository<InventoryLog, Log> {
}
