package com.lalabrand.ecommerce.item.item_comment;

import com.lalabrand.ecommerce.item.Item;
import com.lalabrand.ecommerce.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "item_comment")
public class ItemComment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;

    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false, insertable = false, updatable = false)
    private Item item;

    @Column(name = "item_id", nullable = false, length = 36)
    private String itemId;

    @Lob
    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "rating", nullable = false)
    private Float rating;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

    public static ItemComment fromItemCommentInput(ItemCommentInput itemCommentInput) {
        return ItemComment.builder()
                .text(itemCommentInput.getText())
                .rating(itemCommentInput.getRating())
                .itemId(itemCommentInput.getItemId())
                .userId(itemCommentInput.getUserId())
                .build();
    }
}
