package com.cafe.storeservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(
        columnNames = {"store_id, stat_hour"}
))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class SalesStatsHourly {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(nullable = false)
    private LocalDateTime statHour;

    @Column(nullable = false)
    private Integer orderCount = 0;

    @Column(nullable = false, precision = 12)
    private BigDecimal totalSales = BigDecimal.ZERO;

    @Column(nullable = false, precision = 12)
    private BigDecimal cardSales = BigDecimal.ZERO;

    @Column(nullable = false, precision = 12)
    private BigDecimal cashSales = BigDecimal.ZERO;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
