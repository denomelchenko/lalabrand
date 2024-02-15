package com.lalabrand.ecommerce.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "comment", schema = "lalabrand", indexes = {
        @Index(name = "user_id", columnList = "user_id"),
        @Index(name = "item_id", columnList = "item_id")
})
public class Comment {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Lob
    @Column(name = "text")
    private String text;

    @Column(name = "created_at")
    private Instant createdAt;

}