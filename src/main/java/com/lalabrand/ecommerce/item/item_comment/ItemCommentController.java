package com.lalabrand.ecommerce.item.item_comment;

import com.lalabrand.ecommerce.utils.CommonResponse;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ItemCommentController {
    private final ItemCommentService itemCommentService;

    public ItemCommentController(ItemCommentService itemCommentService) {
        this.itemCommentService = itemCommentService;
    }

    @MutationMapping(name = "itemComment")
    public ItemCommentDTO createItemComment(@Argument(name = "itemCommentInput") ItemCommentInput itemCommentInput) {
        return itemCommentService.createItemComment(itemCommentInput);
    }
}
