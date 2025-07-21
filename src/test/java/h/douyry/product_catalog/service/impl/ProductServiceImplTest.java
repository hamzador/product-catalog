package h.douyry.product_catalog.service.impl;

import h.douyry.product_catalog.dto.CreateProductDto;
import h.douyry.product_catalog.entity.Category;
import h.douyry.product_catalog.entity.Product;
import h.douyry.product_catalog.repository.CategoryRepository;
import h.douyry.product_catalog.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void createProduct_success() {
        CreateProductDto dto = new CreateProductDto("Sac à main", 99.99, 10, 1L);
        Category cat = new Category();
        cat.setId(1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(cat));

        Product product = new Product();
        product.setId(1L);
        product.setName("Sac à main");

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.create(dto);

        assertEquals("Sac à main", result.getName());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void createProduct_withInvalidCategory_throwsException() {
        CreateProductDto dto = new CreateProductDto("Montre", 150.0, 5, 99L);

        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> productService.create(dto));
    }

    @Test
    void deleteProduct_success() {
        Long id = 1L;

        // Mock : le produit existe
        when(productRepository.existsById(id)).thenReturn(true);

        productService.delete(id);

        verify(productRepository).deleteById(id);
    }

    @Test
    void attachProductToCategory_success() {
        Long productId = 1L;
        Long categoryId = 2L;

        Product product = new Product();
        Category category = new Category();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        productService.attachToCategory(productId, categoryId);

        assertEquals(category, product.getCategory());
        verify(productRepository).save(product);
    }
}
