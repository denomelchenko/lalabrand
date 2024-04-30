package com.lalabrand.ecommerce.order.ordered_item;

import com.lalabrand.ecommerce.item.ItemDTO;
import com.lalabrand.ecommerce.item.enums.SizeType;
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
    SizeType sizeType;
    ItemDTO item;
    String title;
    String color;
    String image;
    BigDecimal price;

    public static OrderItemsDTO fromEntity(OrderedItem orderedItem) {
        return OrderItemsDTO.builder()
                .count(orderedItem.getCount())
                .sizeType(orderedItem.getSizeType())
                .item(ItemDTO.fromEntity(orderedItem.getItem()))
                .title(orderedItem.getTitle())
                .color(orderedItem.getColor())
                .image(orderedItem.getImage())
                .price(orderedItem.getPrice())
                .build();
    }

    public OrderedItem toEntity() {
        return OrderedItem.builder()
                .count(this.count)
                .sizeType(this.sizeType)
                .item(this.item.toEntity())
                .title(this.title)
                .color(this.color)
                .image(this.image)
                .price(this.price)
                .build();
    }
}
