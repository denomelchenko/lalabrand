package com.lalabrand.ecommerce.order;

import com.lalabrand.ecommerce.order.enums.Currency;
import com.lalabrand.ecommerce.order.enums.Status;
import com.lalabrand.ecommerce.order.ordered_item.OrderItemsService;
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
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, CartService cartService, OrderItemsServiceImpl orderItemsService, ShippingInfoRepository shippingInfoRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.orderItemsService = orderItemsService;
        this.shippingInfoRepository = shippingInfoRepository;
    }


    @Transactional
    @Override
    public CommonResponse placeOrder(String userId, ShippingInfoRequest shippingInfoRequest, Currency currency) {
        User currentUser = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);

        CartDTO cartDto = cartService.findCartByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("Cart hasn't been found for user ( it's empty )")
        );

        ShippingInfo savedShippingInfo = shippingInfoRepository.save(shippingInfoRequest.toEntity());

        Set<OrderedItem> orderedItems = new HashSet<>();
        Set<CartItem> cartItems = cartDto.toEntity(userRepository.getReferenceById(userId)).getCartItems();

        Float discount = calculateDiscount(userId);

        Order order = Order.builder()
                .user(userRepository.getReferenceById(userId))
                .orderNumber(CommonUtils.getNext())
                .status(Status.PENDING)
                .currency(currency)
                .totalPrice(cartItems.stream().map(cartItem -> cartItem.getItem().getPrice() * cartItem.getCount()).reduce(0.0f, Float::sum))
                .shipping(savedShippingInfo)
                .build();

        applyDiscountAndAdjustBonus(order, discount, currentUser);
        orderRepository.save(order);

        for (CartItem cartItem : cartItems) {
            OrderedItem orderedItem = orderItemsService.generateOrderedFromCartItem(order, cartItem);
            orderItemsService.addOrderedProduct(orderedItem);
            orderedItems.add(orderedItem);
        }
        order.setOrderedItems(orderedItems);

        if (userRepository.findById(userId).isPresent()) {
            currentUser.setBonus((int) ((currentUser.getBonus() + order.getTotalPrice()) - discount.intValue()));
        }

        orderRepository.save(order);
        cartService.deleteCartItems(userId);
        return CommonResponse.builder()
                .message("Order has been placed successfully")
                .success(true)
                .build();
    }

    public void applyDiscountAndAdjustBonus(Order order, Float discount, User currentUser) {
        Float totalPrice = order.getTotalPrice();
        Float bonusToDeduct;
        if (totalPrice.compareTo(discount) >= 0) {
            order.setDiscount(discount);
            bonusToDeduct = discount * 100;
        } else {
            order.setDiscount(totalPrice);
            bonusToDeduct = totalPrice * 100;
        }
        currentUser.setBonus(currentUser.getBonus() - Integer.parseInt(String.valueOf(bonusToDeduct)));
    }

    @Override
    public List<Order> getAllByUserId(String userId) {
        return orderRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public List<Order> getAllByStatus(Status status) {
        return orderRepository.findAllByStatusOrderByCreatedAtAsc(status);
    }

    @Override
    public Float calculateTotal(String userId) {
        CartDTO cartDto = cartService.findCartByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("Cart hasn't been found for user ( it's empty )")
        );
        Set<CartItem> cartItems = cartDto.toEntity(userRepository.getReferenceById(userId)).getCartItems();
        return cartItems.stream()
                .map(cartItem -> cartItem.getItem().getPrice() * cartItem.getCount())
                .reduce(0f, Float::sum);
    }

    @Override
    public Float calculateDiscount(String userId) {
        User existingUser = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("Cart hasn't been found for user ( it's empty )")
        );
        return existingUser.getBonus() / 100f;
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
