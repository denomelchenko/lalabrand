package com.lalabrand.ecommerce.item.category;

import com.lalabrand.ecommerce.item.Item;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", length = 50)
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Item> items;

}
