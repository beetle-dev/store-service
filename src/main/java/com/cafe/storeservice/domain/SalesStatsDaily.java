package com.cafe.storeservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(
        columnNames = {"store_id, stat_date"}
))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class SalesStatsDaily {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(nullable = false)
    private LocalDate statDate;

    @Builder.Default
    @Column(nullable = false)
    private Integer orderCount = 0;

    @Builder.Default
    @Column(nullable = false, precision = 12)
    private BigDecimal totalSales = BigDecimal.ZERO;

    @Builder.Default
    @Column(nullable = false, precision = 12)
    private BigDecimal cardSales = BigDecimal.ZERO;

    @Builder.Default
    @Column(nullable = false, precision = 12)
    private BigDecimal cashSales = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10)
    private BigDecimal avgOrderPrice;

    private Integer peakHour;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;
}
