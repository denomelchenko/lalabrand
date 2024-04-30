package com.lalabrand.ecommerce.order;

import com.lalabrand.ecommerce.item.Item;
import com.lalabrand.ecommerce.item.category.Category;
import com.lalabrand.ecommerce.item.enums.ColorEnum;
import com.lalabrand.ecommerce.item.enums.SizeType;
import com.lalabrand.ecommerce.item.item_info.ItemInfo;
import com.lalabrand.ecommerce.item.size.Size;
import com.lalabrand.ecommerce.order.ordered_item.OrderItemsService;
import com.lalabrand.ecommerce.order.shipping.shipping_info.ShippingInfo;
import com.lalabrand.ecommerce.order.shipping.shipping_info.ShippingInfoRepository;
import com.lalabrand.ecommerce.order.shipping.shipping_info.ShippingInfoRequest;
import com.lalabrand.ecommerce.user.UserRepository;
import com.lalabrand.ecommerce.user.cart.Cart;
import com.lalabrand.ecommerce.user.cart.CartDTO;
import com.lalabrand.ecommerce.user.cart.CartService;
import com.lalabrand.ecommerce.user.cart.cart_item.CartItem;
import com.lalabrand.ecommerce.user.enums.Country;
import com.lalabrand.ecommerce.utils.CommonResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceTests {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartService cartService;

    @Mock
    private OrderItemsService orderItemsService;

    @Mock
    private ShippingInfoRepository shippingInfoRepository;
    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        reset(orderRepository, userRepository, cartService, orderItemsService, shippingInfoRepository);
        orderService = new OrderServiceImpl(orderRepository, userRepository, cartService, orderItemsService, shippingInfoRepository);
    }

    @Test
    void placeOrder_ValidUserIdAndShippingInfoRequest_ReturnsSuccessResponse() {
        String userId = "1";
        ShippingInfoRequest shippingInfoRequest = new ShippingInfoRequest();
        shippingInfoRequest.setAddress1("Test Address");
        shippingInfoRequest.setCity("Test City");
        shippingInfoRequest.setCountry(Country.UA);
        shippingInfoRequest.setZip("12345");
        shippingInfoRequest.setPhone("1234567890");
        shippingInfoRequest.setShippingOptionId("1");

        Item item = Item.builder()
                .id("1")
                .title("Test Item")
                .shortDisc("Short description")
                .longDisc("Long description")
                .rating(new BigDecimal("4.5"))
                .price(new BigDecimal("50.00"))
                .category(new Category())
                .availableCount(100)
                .salePrice(new BigDecimal("45.00"))
                .image("item_image.jpg")
                .soldCount(50)
                .createdAt(Instant.now())
                .build();

        Size size = Size.builder()
                .id("1")
                .sizeType(SizeType.SHOES)
                .value("S")
                .build();

        ItemInfo itemInfo = ItemInfo.builder()
                .id("1")
                .image("item_info_image.jpg")
                .color(ColorEnum.RED)
                .build();

        CartItem cartItem = CartItem.builder()
                .item(item)
                .itemInfo(itemInfo)
                .size(size)
                .count(20)
                .build();

        Cart cart = new Cart();
        Set<CartItem> cartItems = new HashSet<>();
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);

        CartService cartService = mock(CartService.class);
        when(cartService.findCartByUserId(anyString())).thenReturn(Optional.of(CartDTO.fromEntity(cart)));

        ShippingInfoRepository shippingInfoRepository = mock(ShippingInfoRepository.class);
        when(shippingInfoRepository.save(any(ShippingInfo.class))).thenReturn(new ShippingInfo());
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.save(any(Order.class))).thenReturn(new Order());

        OrderService orderService = new OrderServiceImpl(orderRepository, userRepository, cartService, orderItemsService, shippingInfoRepository);
        CommonResponse response = orderService.placeOrder(userId, shippingInfoRequest);
        assertTrue(response.isSuccess());
    }


    @Test
    @Transactional
    void placeOrder_EmptyCart_ReturnsFailureResponse() {
        String userId = "1";
        ShippingInfoRequest shippingInfoRequest = new ShippingInfoRequest();
        when(cartService.findCartByUserId(anyString())).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> orderService.placeOrder(userId, shippingInfoRequest));
        assertEquals("Cart hasn't been found for user ( it's empty )", exception.getMessage());
    }

    @Test
    @Transactional
    void delete_ValidOrderId_ReturnsSuccessResponse() {
        String orderId = "1";
        CommonResponse response = orderService.delete(orderId);
        assertTrue(response.isSuccess());
        assertEquals("Order has been deleted successfully", response.getMessage());
        verify(orderRepository, times(1)).deleteById(orderId);
    }
}
