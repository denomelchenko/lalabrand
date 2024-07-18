package com.lalabrand.ecommerce.item.category;

import com.lalabrand.ecommerce.item.Item;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {
    @Id
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Item> items;

}
