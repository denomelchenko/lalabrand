package com.lalabrand.ecommerce.item.material.items_materials;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemsMaterialsRepository extends JpaRepository<ItemsMaterials, ItemsMaterialsId> {
    Optional<ItemsMaterials> findByMaterialNameAndItemId(String material_name, String item_id);
}