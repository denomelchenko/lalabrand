package com.lalabrand.ecommerce.item.item_comment;

import com.lalabrand.ecommerce.exception.AccessDeniedException;
import com.lalabrand.ecommerce.utils.CommonResponse;
import com.lalabrand.ecommerce.utils.CommonUtils;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
public class ItemCommentController {
    private final ItemCommentService itemCommentService;

    public ItemCommentController(ItemCommentService itemCommentService) {
        this.itemCommentService = itemCommentService;
    }

    @MutationMapping(name = "itemComment")
    @PreAuthorize("hasAuthority('USER')")
    public ItemCommentDTO createItemComment(@Argument(name = "itemCommentInput") ItemCommentInput itemCommentInput) {
        if (CommonUtils.getCurrentUserId().equals(itemCommentInput.getUserId()))
        return itemCommentService.createItemComment(itemCommentInput);
        else throw new AccessDeniedException("Access denied");
    }
}
