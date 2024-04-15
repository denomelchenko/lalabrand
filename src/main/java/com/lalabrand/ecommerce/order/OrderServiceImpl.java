package com.lalabrand.ecommerce.order;

import com.lalabrand.ecommerce.item.enums.Currency;
import com.lalabrand.ecommerce.order.enums.Status;
import com.lalabrand.ecommerce.order.ordered_item.OrderItemsService;
import com.lalabrand.ecommerce.order.ordered_item.OrderedItem;
import com.lalabrand.ecommerce.order.shipping.ShippingInfo;
import com.lalabrand.ecommerce.order.shipping.ShippingInfoDTO;
import com.lalabrand.ecommerce.user.UserRepository;
import com.lalabrand.ecommerce.user.cart.CartDTO;
import com.lalabrand.ecommerce.user.cart.CartService;
import com.lalabrand.ecommerce.user.cart.cart_item.CartItemDTO;
import com.lalabrand.ecommerce.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartService cartService;
    private final OrderItemsService orderItemsService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, CartService cartService, OrderItemsService orderItemsService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.orderItemsService = orderItemsService;
    }

    @Override
    public void placeOrder(String userId, ShippingInfo shippingInfo) {
        if (CommonUtils.isIdInvalid(userId)) {
            throw new IllegalArgumentException("UserId is not valid");
        }
        CartDTO cartDto = cartService.findCartByUserId(userId).orElseThrow(
                    () -> new BadCredentialsException("Cart cannot be null to place the order")
        );

        Set<CartItemDTO> cartItemDtoList = cartDto.getCartItems();
        for (CartItemDTO cartItemDto : cartItemDtoList) {
            OrderedItem orderItem = OrderedItem.builder()
                    .item(cartItemDto.getItem().toEntity())
                    .sizeType(cartItemDto.getSize().getSizeType())
                    .title(cartItemDto.getItem().getTitle())
                    .color(String.valueOf(cartItemDto.getItemInfo().getColor()))
                    .price(cartItemDto.getItem().getPrice())
                    .count(cartItemDto.getCount())
                    .image(cartItemDto.getItem().getImage())
                    .build();
            orderItemsService.addOrderedProducts(orderItem);
        }

        OrderDTO orderDTO = OrderDTO.builder()
                .userId(userId)
                .tax(BigDecimal.TEN) // Need
                .currency(Currency.USD) //Need
                .shippingFee(BigDecimal.valueOf(100)) // Need
                .status(Status.PENDING)
                .totalPrice(cartDto.getTotalCost())
                .shippingInfoDTO(ShippingInfoDTO.fromEntity(ShippingInfo.builder()
                                .id(shippingInfo.getId())
                                .zip(shippingInfo.getZip())
                                .city(shippingInfo.getCity())
                                .address1(shippingInfo.getAddress1())
                                .address2(shippingInfo.getAddress2())
                                .country(shippingInfo.getCountry())
                                .phone(shippingInfo.getPhone())
                        .build()))
                .orderedItems(orderItemsService.getAll())
                .build();

        orderRepository.save(orderDTO.toEntity(shippingInfo, userRepository.getReferenceById(userId)));

        cartService.deleteCartItems();
    }

        @Override
    public List<Order> getAll(String userId) {
        if (CommonUtils.isIdInvalid(userId)) {
            throw new IllegalArgumentException("UserId is not valid");
        }
        return orderRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public void delete(String orderId) {
        orderRepository.deleteById(orderId);
    }
}
