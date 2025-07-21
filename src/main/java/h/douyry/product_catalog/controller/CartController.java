package h.douyry.product_catalog.controller;

import h.douyry.product_catalog.dto.AddProductToCartDto;
import h.douyry.product_catalog.dto.UpdateQuantityDto;
import h.douyry.product_catalog.entity.Cart;
import h.douyry.product_catalog.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Cart> create() {
        return ResponseEntity.ok(service.createCart());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getCart(id));
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<Cart> addItem(@PathVariable Long id, @Valid @RequestBody AddProductToCartDto dto) {
        return ResponseEntity.ok(service.addProduct(id, dto));
    }

    @PutMapping("/{id}/items/{itemId}")
    public ResponseEntity<Cart> updateQty( @PathVariable Long id,
                                           @PathVariable Long itemId,
                                           @Valid @RequestBody UpdateQuantityDto dto) {
        return ResponseEntity.ok(service.updateQuantity(id, itemId, dto.quantity()));
    }

    @DeleteMapping("/{id}/items/{itemId}")
    public ResponseEntity<Cart> deleteItem(@PathVariable Long id, @PathVariable Long itemId) {
        return ResponseEntity.ok(service.removeItem(id, itemId));
    }
}
