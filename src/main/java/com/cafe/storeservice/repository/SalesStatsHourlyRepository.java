package com.cafe.storeservice.repository;

import com.cafe.storeservice.domain.SalesStatsHourly;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesStatsHourlyRepository extends JpaRepository<SalesStatsHourly, Long> {
}
