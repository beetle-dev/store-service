package com.cafe.storeservice.domain.inventory;

import com.cafe.storeservice.domain.BaseEntity;
import com.cafe.storeservice.domain.menu.Ingredient;
import com.cafe.storeservice.domain.store.Store;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

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
