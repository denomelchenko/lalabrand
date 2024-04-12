package com.lalabrand.ecommerce.order.ordered_item;

import com.lalabrand.ecommerce.item.ItemDTO;
import com.lalabrand.ecommerce.item.enums.SizeType;
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
    private BigDecimal price;
    private Integer count;
    private String title;
    private SizeType sizeType;
    private String color;
    private String image;
    private ItemDTO item;


    public static OrderItemsDTO fromEntity(OrderedItem orderedItem){
        return OrderItemsDTO.builder()
                .price(orderedItem.getPrice())
                .color(orderedItem.getColor())
                .title(orderedItem.getTitle())
                .sizeType(orderedItem.getSizeType())
                .color(orderedItem.getColor())
                .image(orderedItem.getImage())
                .item(ItemDTO.fromEntity(orderedItem.getItem()))
                .build();
    }

    public OrderedItem toEntity() {
        OrderedItem orderedItem = new OrderedItem();
        orderedItem.setPrice(this.price);
        orderedItem.setCount(this.count);
        orderedItem.setTitle(this.title);
        orderedItem.setSizeType(this.sizeType);
        orderedItem.setColor(this.color);
        orderedItem.setImage(this.image);
        orderedItem.setItem(this.item != null ? this.item.toEntity() : null);
        return orderedItem;
    }
}
