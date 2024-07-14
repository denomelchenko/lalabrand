package com.lalabrand.ecommerce.item.material.items_materials;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsMaterialsRepository extends JpaRepository<ItemsMaterials, ItemsMaterialsId> {
}