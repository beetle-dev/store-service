package com.cafe.storeservice.repository;

import com.cafe.storeservice.domain.MenuCategories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuCategoryRepository extends JpaRepository<MenuCategories, Long> {
    Optional<MenuCategories> findByNameContaining(String menuCategory);
}
