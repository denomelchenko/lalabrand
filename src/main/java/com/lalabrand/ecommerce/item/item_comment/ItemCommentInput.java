package com.lalabrand.ecommerce.item.item_comment;

import com.lalabrand.ecommerce.utils.annotation.Id;
import com.lalabrand.ecommerce.utils.annotation.Rating;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ItemCommentInput {
    @Size(max = 500, message = "Text must be less than 500 characters")
    private String text;

    @Rating
    private BigDecimal rating;

    @Id(message = "Item ID is invalid")
    private String itemId;

    @Id(message = "User ID is invalid")
    private String userId;
}
