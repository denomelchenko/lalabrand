package com.lalabrand.ecommerce.order;

import com.lalabrand.ecommerce.order.enums.Currency;
import com.lalabrand.ecommerce.order.enums.Status;
import com.lalabrand.ecommerce.order.ordered_item.OrderItemsServiceImpl;
import com.lalabrand.ecommerce.order.ordered_item.OrderedItem;
import com.lalabrand.ecommerce.order.shipping.shipping_info.ShippingInfo;
import com.lalabrand.ecommerce.order.shipping.shipping_info.ShippingInfoRepository;
import com.lalabrand.ecommerce.order.shipping.shipping_info.ShippingInfoRequest;
import com.lalabrand.ecommerce.user.User;
import com.lalabrand.ecommerce.user.UserRepository;
import com.lalabrand.ecommerce.user.cart.CartDTO;
import com.lalabrand.ecommerce.user.cart.CartService;
import com.lalabrand.ecommerce.user.cart.cart_item.CartItem;
import com.lalabrand.ecommerce.utils.CommonResponse;
import com.lalabrand.ecommerce.utils.CommonUtils;
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
    private final OrderItemsServiceImpl orderItemsService;
    private final ShippingInfoRepository shippingInfoRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, CartService cartService, OrderItemsServiceImpl orderItemsService, ShippingInfoRepository shippingInfoRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.orderItemsService = orderItemsService;
        this.shippingInfoRepository = shippingInfoRepository;
    }


    @Transactional
    @Override
    public CommonResponse placeOrder(String userId, ShippingInfoRequest shippingInfoRequest, Currency currency ) {
        CartDTO cartDto = cartService.findCartByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("Cart hasn't been found for user ( it's empty )")
        );

        ShippingInfo savedShippingInfo = shippingInfoRepository.save(shippingInfoRequest.toEntity());

        Set<OrderedItem> orderedItems = new HashSet<>();
        Set<CartItem> cartItems = cartDto.toEntity(userRepository.getReferenceById(userId)).getCartItems();

        Order order = Order.builder()
                .user(userRepository.getReferenceById(userId))
                .orderNumber(CommonUtils.getNext())
                .status(Status.PENDING)
                .currency(currency)
                .tax(BigDecimal.ZERO) // need
                .discount(BigDecimal.ZERO) // need
                .totalPrice(cartItems.stream().map(cartItem -> cartItem.getItem().getPrice().multiply(BigDecimal.valueOf(cartItem.getCount())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .shipping(savedShippingInfo)
                .build();

        orderRepository.save(order);

        for (CartItem cartItem : cartItems) {
            OrderedItem orderItem = OrderedItem.builder()
                    .order(order)
                    .item(cartItem.getItem())
                    .title(cartItem.getItem().getTitle())
                    .color(String.valueOf(cartItem.getItemInfo().getColor()))
                    .image(cartItem.getItemInfo().getImage())
                    .sizeType(cartItem.getSize().getSizeType())
                    .count(cartItem.getCount())
                    .price(cartItem.getItem().getPrice().multiply(BigDecimal.valueOf(cartItem.getCount())))
                    .build();
            orderItemsService.addOrderedProducts(orderItem);
            orderedItems.add(orderItem);
        }
        order.setOrderedItems(orderedItems);

        if (userRepository.findById(userId).isPresent()) {
            User currentUser = userRepository.findById(userId).get();
            currentUser.setBonus(BigDecimal.valueOf(currentUser.getBonus()).add(order.getTotalPrice()).intValue());
        }

        orderRepository.save(order);
        cartService.deleteCartItems(userId);
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
