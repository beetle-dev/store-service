package com.cafe.storeservice.repository;

import com.cafe.storeservice.domain.StoreInventory;
import com.cafe.storeservice.dto.StoreInventoryResDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreInventoryRepository extends JpaRepository<StoreInventory, Long> {
    List<StoreInventoryResDto> findAllByStoreId(Long id);

    Optional<StoreInventory> findByStoreIdAndIngredientId(Long storeId, Long ingredientId);
}
