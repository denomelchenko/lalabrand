package com.lalabrand.ecommerce.item.material;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "materials", schema = "lalabrand")
public class Material {
    @Id
    @Column(name = "name", nullable = false, length = 30)
    private String name;
}