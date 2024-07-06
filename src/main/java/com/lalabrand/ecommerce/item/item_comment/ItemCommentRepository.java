package com.lalabrand.ecommerce.item.item_comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCommentRepository extends JpaRepository<ItemComment, String> {
}
