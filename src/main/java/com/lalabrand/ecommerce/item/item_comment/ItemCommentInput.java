package com.lalabrand.ecommerce.item.item_comment;

import com.lalabrand.ecommerce.utils.annotation.Rating;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.UUID;

@Data
@Builder
public class ItemCommentInput {
    @Size(max = 500, message = "Text must be less than 500 characters")
    private String text;

    @Rating
    private Float rating;

    @UUID(message = "Item ID is invalid")
    private String itemId;

    @UUID(message = "User ID is invalid")
    private String userId;
}
