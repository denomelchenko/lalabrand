package com.lalabrand.ecommerce.item.material;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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