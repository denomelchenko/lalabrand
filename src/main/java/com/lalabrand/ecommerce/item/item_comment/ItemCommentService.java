package com.lalabrand.ecommerce.item.item_comment;

import org.springframework.stereotype.Service;

@Service
public class ItemCommentService {
    private final ItemCommentRepository itemCommentRepository;

    public ItemCommentService(ItemCommentRepository itemCommentRepository) {
        this.itemCommentRepository = itemCommentRepository;
    }

    public ItemCommentDTO createItemComment(ItemCommentInput itemCommentInput) {
        return ItemCommentDTO.fromEntity(
                itemCommentRepository.save(ItemComment.fromItemCommentInput(itemCommentInput))
        );
    }
}
