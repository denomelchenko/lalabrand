package com.lalabrand.ecommerce.item.look;

import com.lalabrand.ecommerce.item.Item;
import com.lalabrand.ecommerce.item.enums.Gender;
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
@Table(name = "look")
public class Look {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "image", nullable = false)
    private String image;

    @ManyToMany
    @JoinTable(name = "look_item",
            joinColumns = @JoinColumn(name = "look_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Set<Item> items;

}