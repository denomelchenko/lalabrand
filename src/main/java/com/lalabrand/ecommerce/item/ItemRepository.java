package com.lalabrand.ecommerce.item;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findItemsByOrderBySoldCountDesc(Pageable pageable);
}