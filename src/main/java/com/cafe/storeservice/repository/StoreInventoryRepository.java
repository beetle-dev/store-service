package com.cafe.storeservice.repository;

import com.cafe.storeservice.domain.StoreInventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreInventoryRepository extends JpaRepository<StoreInventory, Long> {
    Page<StoreInventory> findAllByStoreId(Long id, Pageable pageable);

    Optional<StoreInventory> findByStoreIdAndIngredientId(Long storeId, Long ingredientId);
}
