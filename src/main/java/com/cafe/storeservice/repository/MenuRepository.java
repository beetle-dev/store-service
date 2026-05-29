package com.cafe.storeservice.repository;

import com.cafe.storeservice.domain.menu.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Page<Menu> findAll(Specification<Menu> search, Pageable pageable);

    Optional<Menu> findByName(String name);

    List<Menu> findByStoreIdAndIsActiveTrue(Long id);
}
