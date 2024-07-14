package com.lalabrand.ecommerce.item.material.items_materials;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ItemsMaterialsId implements Serializable {
    private static final long serialVersionUID = -6923532036602332442L;
    @Size(max = 36)
    @NotNull
    @Column(name = "item", nullable = false, length = 36)
    private String item;

    @NotNull
    @Column(name = "material_name", nullable = false)
    private Integer material_name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ItemsMaterialsId entity = (ItemsMaterialsId) o;
        return Objects.equals(this.item, entity.item) &&
                Objects.equals(this.material_name, entity.material_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, material_name);
    }

}