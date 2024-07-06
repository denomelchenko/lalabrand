package com.lalabrand.ecommerce.item.material;

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
public class ItemsMaterialId implements Serializable {
    private static final long serialVersionUID = -6923532036602332442L;
    @Size(max = 36)
    @NotNull
    @Column(name = "item", nullable = false, length = 36)
    private String item;

    @NotNull
    @Column(name = "material_id", nullable = false)
    private Integer materialId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ItemsMaterialId entity = (ItemsMaterialId) o;
        return Objects.equals(this.item, entity.item) &&
                Objects.equals(this.materialId, entity.materialId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, materialId);
    }

}