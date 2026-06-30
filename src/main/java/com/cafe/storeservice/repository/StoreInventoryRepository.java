package com.cafe.storeservice.repository;

import com.cafe.storeservice.domain.inventory.StoreInventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreInventoryRepository extends JpaRepository<StoreInventory, Long> {
    Optional<StoreInventory> findByIngredientId(Long ingredientId);

    Page<StoreInventory> findAll(Specification<StoreInventory> search, Pageable pageable);
}
