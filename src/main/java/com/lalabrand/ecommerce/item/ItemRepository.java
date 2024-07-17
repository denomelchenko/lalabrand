package com.lalabrand.ecommerce.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
    Page<Item> findItemsByCategoryId(String categoryId, Pageable pageable);

    Page<Item> findItemsByOrderBySoldCountDesc(Pageable pageable);

    Page<Item> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Query("select o from Item o where o.id in :itemsIds")
    Set<Item> findAllByIds(Collection<String> itemsIds);
}
