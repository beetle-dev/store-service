package com.cafe.storeservice.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(
        columnNames = {"store_id", "stat_hour"}
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

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
