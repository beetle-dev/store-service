package com.cafe.storeservice.repository;

import com.cafe.storeservice.domain.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {
    Optional<MenuCategory> findByName(String name);
}
