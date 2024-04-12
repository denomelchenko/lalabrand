package com.lalabrand.ecommerce.item.item_info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemInfoRepository extends JpaRepository<ItemInfo, String> {
}
