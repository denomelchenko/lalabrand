package com.lalabrand.ecommerce.item;

import org.springframework.data.domain.Page;
import com.lalabrand.ecommerce.item.enums.ColorEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
    Page<Item> findItemsByCategoryNameIgnoreCase(String categoryName, Pageable pageable);

    Page<Item> findItemsByOrderBySoldCountDesc(Pageable pageable);

    Page<Item> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Query("select o from Item o where o.id in :itemsIds")
    Set<Item> findAllByIds(Collection<String> itemsIds);

    @Query("SELECT i FROM Item i " +
            "JOIN i.itemInfos ii " +
            "JOIN ii.sizes iii " +
            "WHERE (:categoryName IS NULL OR i.category.name = :categoryName) " +
            "AND (:color IS NULL OR ii.color = :color) " +
            "AND (:sizeId IS NULL OR iii.id = :sizeId)")
    List<Item> findFilteredItems(@Param("categoryName") String categoryName,
                                 @Param("color") ColorEnum color,
                                 @Param("sizeId") String sizeId);
}
