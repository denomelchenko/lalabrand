package com.lalabrand.ecommerce.item.item_comment;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO for {@link ItemComment}
 */
@Value
@Builder
public class ItemCommentDTO implements Serializable {
    String id;
    String userId;
    String itemId;
    String text;
    BigDecimal rating;

    public static ItemCommentDTO fromEntity(ItemComment itemComment) {
        return ItemCommentDTO.builder()
                .id(itemComment.getId())
                .userId(itemComment.getUserId())
                .itemId(itemComment.getItemId())
                .text(itemComment.getText())
                .rating(itemComment.getRating())
                .build();
    }
}