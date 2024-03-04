package com.lalabrand.ecommerce.item.color;

import com.lalabrand.ecommerce.item.Item;
import com.lalabrand.ecommerce.item.enums.ColorEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "color")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "color", nullable = false)
    @Enumerated(EnumType.STRING)
    private ColorEnum color;

    @ManyToMany
    @JoinTable(name = "items_colors",
            joinColumns = @JoinColumn(name = "color_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Set<Item> items = new LinkedHashSet<>();

}