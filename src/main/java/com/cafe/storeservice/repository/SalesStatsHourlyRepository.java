package com.cafe.storeservice.repository;

import com.cafe.storeservice.domain.salesQuery.SalesStatsHourly;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface SalesStatsHourlyRepository extends JpaRepository<SalesStatsHourly, Long> {
    Page<SalesStatsHourly> findByStoreIdAndStatHourBetween(Long id, LocalDateTime from, LocalDateTime to, Pageable pageable);
}
