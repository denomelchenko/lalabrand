package com.lalabrand.ecommerce.user.cart;

import com.lalabrand.ecommerce.item.item_info.ItemInfo;
import com.lalabrand.ecommerce.item.item_info.ItemInfoRepository;
import com.lalabrand.ecommerce.item.size.Size;
import com.lalabrand.ecommerce.item.size.SizeRepository;
import com.lalabrand.ecommerce.user.cart.cart_item.CartItem;
import com.lalabrand.ecommerce.user.cart.cart_item.CartItemDTO;
import com.lalabrand.ecommerce.user.cart.cart_item.CartItemRepository;
import com.lalabrand.ecommerce.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ItemInfoRepository itemInfoRepository;
    private final Logger logger = LoggerFactory.getLogger(CartService.class);
    private final SizeRepository sizeRepository;
    private final CartItemRepository cartItemRepository;

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
        if (CommonUtils.isIdInvalid(userId)) {
            logger.error("UserId: " + userId + "is not valid");
            throw new IllegalArgumentException("UserId is not valid");
        }
        Optional<Cart> cart = cartRepository.findCartByUserId(userId);
        if (cart.isEmpty() || cart.get().getCartItems().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(CartDTO.fromEntity(cart.get()));
    }

    public CartDTO addItemToCart(String itemId, String itemInfoId, String sizeId, Integer count, String userId) {
        if (count == 0) {
            return null;
        }

        if (CommonUtils.isIdInvalid(itemId) || CommonUtils.isIdInvalid(itemInfoId)
                || CommonUtils.isIdInvalid(sizeId) || CommonUtils.isIdInvalid(userId)) {
            logger.error("One of ids is not valid (itemId: {}, itemInfoId: {}, sizeId: {}, userId: {})",
                    itemId, itemInfoId, sizeId, userId);
            throw new IllegalArgumentException("Id is not valid");
        }
        Optional<ItemInfo> itemInfo = itemInfoRepository.findById(itemInfoId);
        if (itemInfo.isEmpty()) {
            logger.error("ItemInfo with id: {} does not exist", itemInfoId);
            throw new IllegalArgumentException("Item info with id: " + itemInfoId + " does not exist");
        }
        if (!Objects.equals(itemInfo.get().getItem_id(), itemId)) {
            logger.error("ItemInfo with id: {} is not for item with id: {}", itemInfoId, itemId);
            throw new IllegalArgumentException("ItemInfo with id: " + itemInfoId + " is not for item with id: " + itemId);
        }

        Optional<Size> size = sizeRepository.findById(sizeId);
        if (size.isEmpty()) {
            logger.error("Size with id: {} does not exist", sizeId);
            throw new IllegalArgumentException("Size with id: " + sizeId + " does not exist");
        }
        if (size.get().getItems().stream().noneMatch(item -> item.getId().equals(itemId))) {
            logger.error("Size with id: {} is not for item with id: {}", sizeId, itemId);
            throw new IllegalArgumentException("Size with id: " + sizeId + " is not for item with id: " + itemId);
        }

        Optional<Cart> existCart = cartRepository.findCartByUserId(userId);
        if (existCart.isEmpty()) {
            CartDTO cart = CartDTO.fromEntity(cartRepository.save(new Cart(userId)));
            CartItem cartItem = cartItemRepository.save(new CartItem(itemId, itemInfoId, sizeId, count, cart.getId()));
            cart.getCartItems().add(CartItemDTO.fromEntity(cartItem));
            return cart;
        }
        CartItem cartItem = cartItemRepository.save(new CartItem(itemId, itemInfoId, sizeId, count, existCart.get().getId()));
        existCart.get().setCartItems(cartItemRepository.findCartItemsByCartId(existCart.get().getId()));
        return CartDTO.fromEntity(existCart.get());
    }
}
