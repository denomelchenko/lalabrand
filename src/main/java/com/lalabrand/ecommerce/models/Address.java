package com.lalabrand.ecommerce.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "address", schema = "lalabrand", indexes = {
        @Index(name = "user_id", columnList = "user_id")
})
public class Address {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "country")
    private String country;

    @Column(name = "zip")
    private String zip;

    @Column(name = "city")
    private String city;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}