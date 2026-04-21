package com.cafe.storeservice.repository;

import com.cafe.storeservice.domain.SalesStatsDaily;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesStatsDailyRepository extends JpaRepository<SalesStatsDaily, Long> {
}
