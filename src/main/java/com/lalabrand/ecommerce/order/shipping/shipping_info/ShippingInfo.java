package com.lalabrand.ecommerce.order.shipping.shipping_info;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lalabrand.ecommerce.order.Order;
import com.lalabrand.ecommerce.order.shipping.shipping_option.ShippingOption;
import com.lalabrand.ecommerce.user.enums.Country;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shipping_info")
public class ShippingInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "country", nullable = false, columnDefinition = "ENUM('UA', 'PL', 'DE', 'US', 'UK')")
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

    @OneToOne(mappedBy = "shipping")
    @JsonBackReference
    private Order order;

    @Column(name = "phone")
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "shipping_option_id", insertable = false, updatable = false)
    private ShippingOption shippingOption;

    @Column(name = "shipping_option_id")
    private String shippingOptionId;
}
