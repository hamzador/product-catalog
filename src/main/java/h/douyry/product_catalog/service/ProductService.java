package h.douyry.product_catalog.service;

import h.douyry.product_catalog.dto.CreateProductDto;
import h.douyry.product_catalog.entity.Product;

import java.util.List;

public interface ProductService {

    Product create(CreateProductDto dto);
    Product update(Long id, CreateProductDto dto);
    void delete(Long id);
    List<Product> getAll();
    void attachToCategory(Long productId, Long categoryId);
    void detachFromCategory(Long productId);
}
