package com.lalabrand.ecommerce.item;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
    List<Item> findItemsByCategoryId(String categoryId);

    List<Item> findItemsByOrderBySoldCountDesc(Pageable pageable);

    List<Item> findByTitleContainingIgnoreCase(String title);
}
