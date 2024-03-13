package com.lalabrand.ecommerce.order.shipping_info;

import com.lalabrand.ecommerce.order.Order;
import com.lalabrand.ecommerce.user.enums.Country;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "shipping_info")
public class ShippingInfo {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "price", nullable = false, precision = 10)
    private BigDecimal price;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "country", nullable = false)
    @Enumerated(EnumType.STRING)
    private Country country;

    @Column(name = "zip", nullable = false, length = 10)
    private String zip;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "address1", nullable = false, length = 50)
    private String address1;

    @Column(name = "address2", nullable = false, length = 50)
    private String address2;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @OneToMany(mappedBy = "shipping")
    private Set<Order> orders;

}