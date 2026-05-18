package com.cafe.storeservice.repository;

import com.cafe.storeservice.domain.salesQuery.SalesStatsDaily;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface SalesStatsDailyRepository extends JpaRepository<SalesStatsDaily, Long> {
    Page<SalesStatsDaily> findByStoreIdAndStatDateBetween(Long id, LocalDate from, LocalDate to, Pageable pageable);
}
