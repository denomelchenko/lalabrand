package com.lalabrand.ecommerce.item.material.items_materials;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemsMaterialsId implements Serializable {
    private static final long serialVersionUID = -6923532036602332442L;

    @Size(max = 36)
    @NotNull
    @Column(name = "item_id", nullable = false, length = 36)
    private String item_id;

    @NotNull
    @Column(name = "material_name", nullable = false)
    private String material_name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ItemsMaterialsId entity = (ItemsMaterialsId) o;
        return Objects.equals(this.item_id, entity.item_id) &&
                Objects.equals(this.material_name, entity.material_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item_id, material_name);
    }

}