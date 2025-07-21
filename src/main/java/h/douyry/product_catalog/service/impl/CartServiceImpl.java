package h.douyry.product_catalog.service.impl;

import h.douyry.product_catalog.dto.AddProductToCartDto;
import h.douyry.product_catalog.entity.Cart;
import h.douyry.product_catalog.entity.CartItem;
import h.douyry.product_catalog.entity.Product;
import h.douyry.product_catalog.repository.CartRepository;
import h.douyry.product_catalog.repository.ProductRepository;
import h.douyry.product_catalog.service.CartService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Cart createCart() {
        return cartRepository.save(new Cart());
    }

    @Override
    public Cart addProduct(Long cartId, AddProductToCartDto dto) {
        Cart cart = getCart(cartId);
        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new NoSuchElementException("Produit introuvable"));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(dto.productId()))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + dto.quantity());
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(dto.quantity());
            newItem.setCart(cart);
            cart.getItems().add(newItem);
        }

        return cartRepository.save(cart);
    }

    @Override
    public Cart updateQuantity(Long cartId, Long itemId, int quantity) {
        Cart cart = getCart(cartId);
        CartItem item = cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Article introuvable"));

        item.setQuantity(quantity);
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeItem(Long cartId, Long itemId) {
        Cart cart = getCart(cartId);
        cart.getItems().removeIf(i -> i.getId().equals(itemId));
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCart(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new NoSuchElementException("Panier introuvable"));
    }
}
