package com.lalabrand.ecommerce.item;

import com.lalabrand.ecommerce.user.enums.Language;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
    List<Item> findItemsByCategoryId(String categoryId, Pageable pageable);

    List<Item> findItemsByOrderBySoldCountDesc(Pageable pageable);

    List<Item> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Query("select o from Item o where o.id in :itemsIds")
    Set<Item> findAllByIds(Collection<String> itemsIds);

    @Query("select  i FROM Item i " +
            "JOIN i.itemInfos ii " +
            "WHERE (:categoryId IS NULL OR i.category.id = :categoryId) " +
            "AND (:sizeId IS NULL OR ii.size.id = :sizeId) " +
            "AND (:color IS NULL OR ii.color = :color)")
    List<ItemDTO> findFilteredItems(
            @Param("categoryId") String categoryId,
            @Param("sizeId") String sizeId,
            @Param("color") String color,
            Pageable pageable);
}
