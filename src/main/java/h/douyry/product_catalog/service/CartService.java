package h.douyry.product_catalog.service;

import h.douyry.product_catalog.dto.AddProductToCartDto;
import h.douyry.product_catalog.entity.Cart;

public interface CartService {
    Cart createCart();
    Cart getCart(Long cartId);
    Cart addProduct(Long cartId, AddProductToCartDto dto);
    Cart updateQuantity(Long cartId, Long itemId, int quantity);
    Cart removeItem(Long cartId, Long itemId);
}
