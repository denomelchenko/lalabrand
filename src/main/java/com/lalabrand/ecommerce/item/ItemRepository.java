package com.lalabrand.ecommerce.item;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
    List<Item> findItemsByCategoryId(String categoryId);

    List<Item> findItemsByOrderBySoldCountDesc(Pageable pageable);

    List<Item> findByTitleContainingIgnoreCase(String title);

    @Query("select o from Item o where o.id in :itemsIds")
    Set<Item> findAllByIds(Collection<String> itemsIds);
}
