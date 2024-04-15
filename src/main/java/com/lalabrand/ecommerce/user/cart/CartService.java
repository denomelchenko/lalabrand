package com.lalabrand.ecommerce.user.cart;

import com.lalabrand.ecommerce.item.Item;
import com.lalabrand.ecommerce.item.ItemRepository;
import com.lalabrand.ecommerce.item.item_info.ItemInfo;
import com.lalabrand.ecommerce.item.item_info.ItemInfoRepository;
import com.lalabrand.ecommerce.item.size.Size;
import com.lalabrand.ecommerce.item.size.SizeRepository;
import com.lalabrand.ecommerce.user.cart.cart_item.CartItem;
import com.lalabrand.ecommerce.user.cart.cart_item.CartItemDTO;
import com.lalabrand.ecommerce.user.cart.cart_item.CartItemRepository;
import com.lalabrand.ecommerce.utils.CommonResponse;
import com.lalabrand.ecommerce.utils.CommonUtils;
import jakarta.transaction.Transactional;
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
            logger.error("UserId: {}is not valid", userId);
            throw new IllegalArgumentException("UserId is not valid");
        }
        Optional<Cart> cart = cartRepository.findCartByUserId(userId);
        if (cart.isEmpty() || cart.get().getCartItems().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(CartDTO.fromEntity(cart.get()));
    }

    @Transactional
    public CommonResponse addItemToCart(String itemId, String itemInfoId, String sizeId, Integer count, String userId) {
        if (count <= 0) {
            throw new IllegalArgumentException("Count must be greater than zero");
        }

        if (CommonUtils.isIdInvalid(itemId) || CommonUtils.isIdInvalid(itemInfoId)
                || CommonUtils.isIdInvalid(sizeId) || CommonUtils.isIdInvalid(userId)) {
            logger.error("One of ids is not valid (itemId: {}, itemInfoId: {}, sizeId: {}, userId: {})",
                    itemId, itemInfoId, sizeId, userId);
            throw new IllegalArgumentException("Id is not valid");
        }

        Optional<ItemInfo> itemInfo = itemInfoRepository.findById(itemInfoId);
        itemInfo.orElseThrow(() -> {
            logger.error("ItemInfo with id: {} does not exist", itemInfoId);
            return new IllegalArgumentException("Item info with id: " + itemInfoId + " does not exist");
        });
        if (!itemInfo.get().getItem_id().equals(itemId)) {
            logger.error("ItemInfo with id: {} is not for item with id: {}", itemInfoId, itemId);
            throw new IllegalArgumentException("ItemInfo with id: " + itemInfoId + " is not for item with id: " + itemId);
        }

        Optional<Cart> existCart = cartRepository.findCartByUserId(userId);

        Optional<Size> size = sizeRepository.findById(sizeId);
        size.orElseThrow(() -> {
            logger.error("Size with id: {} does not exist", sizeId);
            return new IllegalArgumentException("Size with id: " + sizeId + " does not exist");
        });
        if (size.get().getItems().stream().noneMatch(item -> item.getId().equals(itemId))) {
            logger.error("Size with id: {} is not for item with id: {}", sizeId, itemId);
            throw new IllegalArgumentException("Size with id: " + sizeId + " is not for item with id: " + itemId);
        }

        existCart.ifPresentOrElse(
                cart -> {
                    boolean cartItemAlreadyExist = false;
                    for (CartItem cartItem : cart.getCartItems()) {
                        if (cartItem.getItemId().equals(itemId)
                                && cartItem.getItemInfoId().equals(itemInfoId)
                                && cartItem.getSizeId().equals(sizeId)) {
                            cartItem.setCount(cartItem.getCount() + count);
                            cartItemRepository.save(cartItem);
                            cartItemAlreadyExist = true;
                            break;
                        }
                    }
                    if (!cartItemAlreadyExist) {
                        cartItemRepository.save(new CartItem(itemId, itemInfoId, sizeId, count, cart.getId()));
                    }
                },
                () -> {
                    Cart newCart = cartRepository.save(new Cart(userId));
                    cartItemRepository.save(new CartItem(itemId, itemInfoId, sizeId, count, newCart.getId()));
                }
        );
        return CommonResponse.builder()
                .success(true)
                .message("Item added to cart successfully")
                .build();
    }
}
