package h.douyry.product_catalog.controller;

import h.douyry.product_catalog.dto.CreateProductDto;
import h.douyry.product_catalog.entity.Product;
import h.douyry.product_catalog.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody CreateProductDto dto) {
        return ResponseEntity.ok(productService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody CreateProductDto dto) {
        return ResponseEntity.ok(productService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{productId}/attach-category/{categoryId}")
    public ResponseEntity<Void> attachToCategory(@PathVariable Long productId, @PathVariable Long categoryId) {
        productService.attachToCategory(productId, categoryId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{productId}/detach-category")
    public ResponseEntity<Void> detachFromCategory(@PathVariable Long productId) {
        productService.detachFromCategory(productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }
}
