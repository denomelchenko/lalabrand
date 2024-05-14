package com.lalabrand.ecommerce.user.cart;

import com.lalabrand.ecommerce.item.item_info.ItemInfo;
import com.lalabrand.ecommerce.item.item_info.ItemInfoRepository;
import com.lalabrand.ecommerce.item.size.Size;
import com.lalabrand.ecommerce.item.size.SizeRepository;
import com.lalabrand.ecommerce.user.cart.cart_item.CartItem;
import com.lalabrand.ecommerce.user.cart.cart_item.CartItemRepository;
import com.lalabrand.ecommerce.user.cart.cart_item.CartItemRequest;
import com.lalabrand.ecommerce.utils.CommonResponse;
import com.lalabrand.ecommerce.utils.CommonUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ItemInfoRepository itemInfoRepository;
    private final SizeRepository sizeRepository;
    private final CartItemRepository cartItemRepository;
    private final Logger logger = LoggerFactory.getLogger(CartService.class);

    @Autowired
    public CartService(final CartRepository cartRepository, ItemInfoRepository itemInfoRepository,
                       SizeRepository sizeRepository,
                       CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.itemInfoRepository = itemInfoRepository;
        this.sizeRepository = sizeRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public Optional<CartDTO> findCartByUserId(String userId) {
        Optional<Cart> cart = cartRepository.findCartByUserId(userId);
        if (cart.isEmpty() || cart.get().getCartItems().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(CartDTO.fromEntity(cart.get()));
    }

    @Transactional
    public CommonResponse addItemToCart(CartItemRequest cartItemRequest, String userId) {
        if (cartItemRequest.getCount() <= 0) {
            throw new IllegalArgumentException("Count must be greater than zero");
        }

        Optional<ItemInfo> itemInfo = itemInfoRepository.findById(cartItemRequest.getItemInfoId());
        itemInfo.orElseThrow(() -> {
            logger.error("ItemInfo with id: {} does not exist", cartItemRequest.getItemInfoId());
            return new IllegalArgumentException("Item info with id: " + cartItemRequest.getItemInfoId() + " does not exist");
        });
        if (!itemInfo.get().getItem_id().equals(cartItemRequest.getItemId())) {
            logger.error("ItemInfo with id: {} is not for item with id: {}", cartItemRequest.getItemInfoId(), cartItemRequest.getItemId());
            throw new IllegalArgumentException("ItemInfo with id: " + cartItemRequest.getItemInfoId() + " is not for item with id: " + cartItemRequest.getItemId());
        }

        Optional<Cart> existCart = cartRepository.findCartByUserId(userId);

        Optional<Size> size = sizeRepository.findById(cartItemRequest.getSizeId());
        size.orElseThrow(() -> {
            logger.error("Size with id: {} does not exist", cartItemRequest.getSizeId());
            return new IllegalArgumentException("Size with id: " + cartItemRequest.getSizeId() + " does not exist");
        });
        if (size.get().getItems().stream().noneMatch(item -> item.getId().equals(cartItemRequest.getItemId()))) {
            logger.error("Size with id: {} is not for item with id: {}", cartItemRequest.getSizeId(), cartItemRequest.getItemId());
            throw new IllegalArgumentException("Size with id: " + cartItemRequest.getSizeId() + " is not for item with id: " + cartItemRequest.getItemId());
        }

        existCart.ifPresentOrElse(
                cart -> {
                    boolean cartItemAlreadyExist = false;
                    for (CartItem cartItem : cart.getCartItems()) {
                        if (cartItem.getItemId().equals(cartItemRequest.getItemId())
                                && cartItem.getItemInfoId().equals(cartItemRequest.getItemInfoId())
                                && cartItem.getSizeId().equals(cartItemRequest.getSizeId())) {
                            cartItem.setCount(cartItem.getCount() + cartItemRequest.getCount());
                            cartItemRepository.save(cartItem);
                            cartItemAlreadyExist = true;
                            break;
                        }
                    }
                    if (!cartItemAlreadyExist) {
                        cartItemRepository.save(new CartItem(
                                cartItemRequest.getItemId(), cartItemRequest.getItemInfoId(),
                                cartItemRequest.getSizeId(), cartItemRequest.getCount(), cart.getId()
                        ));
                    }
                },
                () -> {
                    Cart newCart = cartRepository.save(new Cart(userId));
                    cartItemRepository.save(new CartItem(
                            cartItemRequest.getItemId(), cartItemRequest.getItemInfoId(),
                            cartItemRequest.getSizeId(), cartItemRequest.getCount(), newCart.getId()
                    ));
                }
        );
        return CommonResponse.builder()
                .success(true)
                .message("Item added to cart successfully")
                .build();
    }

    @Transactional
    public CommonResponse removeItemFromCart(CartItemRequest cartItemRequest, String userId) {
        if (cartItemRequest.getCount() <= 0) {
            throw new IllegalArgumentException("Count must be less than zero");
        }

        Optional<Cart> existCart = cartRepository.findCartByUserId(userId);
        if (existCart.isEmpty()) {
            throw new IllegalArgumentException("Cart for user with id: " + userId + " does not exist");
        }

        CartItem cartItem = cartItemRepository.findCartItemByCartIdAndItemIdAndItemInfoIdAndSizeId(
                existCart.get().getId(), cartItemRequest.getItemId(),
                cartItemRequest.getItemInfoId(), cartItemRequest.getSizeId()
        );

        if (cartItem.getCount() < cartItemRequest.getCount()) {
            throw new IllegalArgumentException("Count must be less than current count of this item in the cart");
        } else if (Objects.equals(cartItemRequest.getCount(), cartItem.getCount())) {
            cartItemRepository.deleteById(cartItem.getId());
        } else {
            cartItem.setCount(cartItem.getCount() - cartItemRequest.getCount());
            cartItemRepository.save(cartItem);
        }
        return CommonResponse.builder()
                .message("Item has removed from cart successfully")
                .success(true)
                .build();
    }

    @Transactional
    public void deleteCartItems(String userId) {
        if (cartRepository.findCartByUserId(userId).isPresent()) {
            cartItemRepository.deleteAllByCartId(cartRepository.findCartByUserId(userId).get().getId());
        } else {
            throw new IllegalArgumentException("Cart for user with id: " + userId + " does not exist");
        }
    }
}
