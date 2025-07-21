package h.douyry.product_catalog.service.impl;

import h.douyry.product_catalog.dto.AddProductToCartDto;
import h.douyry.product_catalog.entity.Cart;
import h.douyry.product_catalog.entity.CartItem;
import h.douyry.product_catalog.entity.Product;
import h.douyry.product_catalog.repository.CartRepository;
import h.douyry.product_catalog.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.AssertionsKt.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    void createCart_shouldReturnNewCart() {
        Cart cart = new Cart();
        when(cartRepository.save(any())).thenReturn(cart);

        Cart result = cartService.createCart();

        assertNotNull(result);
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    void addProduct_shouldAddNewItem() {
        Long cartId = 1L, productId = 10L;
        Cart cart = new Cart();
        cart.setId(cartId);

        Product product = new Product();
        product.setId(productId);
        product.setName("Produit A");
        product.setPrice(100.0);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(cartRepository.save(any())).thenReturn(cart);

        AddProductToCartDto dto = new AddProductToCartDto(productId, 2);

        Cart result = cartService.addProduct(cartId, dto);

        assertEquals(1, result.getItems().size());
        CartItem item = result.getItems().get(0);
        assertEquals(product, item.getProduct());
        assertEquals(2, item.getQuantity());
    }

    @Test
    void updateQuantity_shouldChangeQuantity() {
        Long cartId = 1L;
        Long itemId = 20L;

        Product product = new Product();
        product.setPrice(50.0);

        CartItem item = new CartItem();
        item.setId(itemId);
        item.setProduct(product);
        item.setQuantity(1);

        Cart cart = new Cart();
        cart.setId(cartId);
        cart.getItems().add(item);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any())).thenReturn(cart);

        Cart result = cartService.updateQuantity(cartId, itemId, 5);

        assertEquals(5, result.getItems().get(0).getQuantity());
    }

    @Test
    void removeItem_shouldDeleteFromCart() {
        Long cartId = 1L;
        Long itemId = 100L;

        CartItem item = new CartItem();
        item.setId(itemId);

        Cart cart = new Cart();
        cart.setId(cartId);
        cart.getItems().add(item);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any())).thenReturn(cart);

        Cart result = cartService.removeItem(cartId, itemId);

        assertTrue(result.getItems().isEmpty());
    }

    @Test
    void addProduct_shouldThrowWhenProductNotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(new Cart()));

        AddProductToCartDto dto = new AddProductToCartDto(999L, 1);

        assertThrows(NoSuchElementException.class, () -> cartService.addProduct(1L, dto));
    }

    @Test
    void getCart_shouldThrowWhenCartNotFound() {
        when(cartRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> cartService.getCart(999L));
    }
}
