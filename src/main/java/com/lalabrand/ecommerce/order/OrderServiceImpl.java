package com.lalabrand.ecommerce.order;

import com.lalabrand.ecommerce.item.enums.Currency;
import com.lalabrand.ecommerce.order.enums.Status;
import com.lalabrand.ecommerce.order.ordered_item.OrderItemsService;
import com.lalabrand.ecommerce.order.ordered_item.OrderedItem;
import com.lalabrand.ecommerce.order.shipping.shipping_info.ShippingInfo;
import com.lalabrand.ecommerce.order.shipping.shipping_info.ShippingInfoRepository;
import com.lalabrand.ecommerce.order.shipping.shipping_info.ShippingInfoRequest;
import com.lalabrand.ecommerce.user.UserRepository;
import com.lalabrand.ecommerce.user.cart.CartDTO;
import com.lalabrand.ecommerce.user.cart.CartService;
import com.lalabrand.ecommerce.user.cart.cart_item.CartItem;
import com.lalabrand.ecommerce.utils.CommonResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartService cartService;
    private final OrderItemsService orderItemsService;
    private final ShippingInfoRepository shippingInfoRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, CartService cartService, OrderItemsService orderItemsService, ShippingInfoRepository shippingInfoRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.orderItemsService = orderItemsService;
        this.shippingInfoRepository = shippingInfoRepository;
    }


    @Transactional
    @Override
    public CommonResponse placeOrder(String userId, ShippingInfoRequest shippingInfoRequest) {
        CartDTO cartDto = cartService.findCartByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("Cart hasn't been found for user ( it's empty )")
        );

        ShippingInfo shippingInfo = ShippingInfo.builder()
                .address1(shippingInfoRequest.getAddress1())
                .address2(shippingInfoRequest.getAddress2())
                .city(shippingInfoRequest.getCity())
                .country(shippingInfoRequest.getCountry())
                .zip(shippingInfoRequest.getZip())
                .phone(shippingInfoRequest.getPhone())
                .shippingOptionId(shippingInfoRequest.getShippingOptionId())
                .build();

        ShippingInfo savedShippingInfo = shippingInfoRepository.save(shippingInfo);

        Order order = Order.builder()
                .user(userRepository.getReferenceById(userId))
                .status(Status.PENDING)
                .currency(Currency.UAH) // need
                .tax(BigDecimal.ZERO) // need
                .discount(BigDecimal.ZERO) // need
                .shippingFee(BigDecimal.valueOf(0)) // need
                .totalPrice(cartDto.getTotalCost())
                .shipping(savedShippingInfo)
                .build();

        orderRepository.save(order);


        Set<OrderedItem> orderedItems = new HashSet<>();
        Set<CartItem> cartItems = cartDto.toEntity(userRepository.getReferenceById(userId)).getCartItems();
        for (CartItem cartItem : cartItems) {
            OrderedItem orderItem = OrderedItem.builder()
                    .order(order)
                    .item(cartItem.getItem())
                    .itemInfoId(cartItem.getItemInfo().getId())
                    .sizeId(cartItem.getSize().getId())
                    .count(cartItem.getCount())
                    .build();
            orderItemsService.addOrderedProducts(orderItem);
            orderedItems.add(orderItem);
        }
        order.setOrderedItems(orderedItems);

        orderRepository.save(order);
        cartService.deleteCartItems();
        return CommonResponse.builder()
                .message("Order has been placed successfully")
                .success(true)
                .build();
    }


    @Override
    public List<Order> getAll(String userId) {
        return orderRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
    }

    @Transactional
    @Override
    public CommonResponse delete(String orderId) {
        orderRepository.deleteById(orderId);
        return CommonResponse.builder()
                .message("Order has been deleted successfully")
                .success(true)
                .build();
    }
}
