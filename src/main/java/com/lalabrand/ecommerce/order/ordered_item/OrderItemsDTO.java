package com.lalabrand.ecommerce.order.ordered_item;

import com.lalabrand.ecommerce.item.ItemDTO;
import com.lalabrand.ecommerce.item.item_info.ItemInfoDTO;
import com.lalabrand.ecommerce.item.size.SizeDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

@Value
@Getter
@Setter
@Builder
public class OrderItemsDTO implements Serializable {
    Integer count;
    SizeDTO size;
    ItemDTO item;
    ItemInfoDTO itemInfo;

    public static OrderItemsDTO fromEntity(OrderedItem orderedItem) {
        return OrderItemsDTO.builder()
                .count(orderedItem.getCount())
                .size(SizeDTO.fromEntity(orderedItem.getSize()))
                .item(ItemDTO.fromEntity(orderedItem.getItem()))
                .itemInfo(ItemInfoDTO.fromEntity(orderedItem.getItemInfo()))
                .build();
    }

    public OrderedItem toEntity() {
        return OrderedItem.builder()
                .count(this.count)
                .size(this.size.toEntity())
                .item(this.item.toEntity())
                .itemInfo(this.itemInfo.toEntity())
                .build();
    }
}
