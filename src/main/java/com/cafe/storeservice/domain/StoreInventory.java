package com.cafe.storeservice.domain;

import com.cafe.storeservice.dto.InventoryReqDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "store_inventories",
        uniqueConstraints = @UniqueConstraint(
        columnNames = {"store_id", "ingredient_id"}
))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class StoreInventory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Setter
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal currentStock;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal minStock;

    @PrePersist
    protected void prePersist() {
        if (this.currentStock == null) this.currentStock = BigDecimal.ZERO;
        if (this.minStock == null) this.minStock = BigDecimal.ZERO;
    }
}
