package com.lalabrand.ecommerce.item.size;

import com.lalabrand.ecommerce.item.Item;
import com.lalabrand.ecommerce.item.enums.SizeType;
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
@Table(name = "size")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SizeType sizeType;

    @Column(name = "value", nullable = false, length = 40)
    private String value;

    @ManyToMany
    @JoinTable(name = "items_sizes",
            joinColumns = @JoinColumn(name = "size_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Set<Item> items;

}