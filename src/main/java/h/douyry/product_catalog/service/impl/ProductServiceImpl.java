package h.douyry.product_catalog.service.impl;

import h.douyry.product_catalog.dto.CreateProductDto;
import h.douyry.product_catalog.entity.Category;
import h.douyry.product_catalog.entity.Product;
import h.douyry.product_catalog.repository.CategoryRepository;
import h.douyry.product_catalog.repository.ProductRepository;
import h.douyry.product_catalog.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product create(CreateProductDto dto) {
        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Product product = new Product();
        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setStock(dto.stock());
        product.setCategory(category);

        return productRepository.save(product);
    }

    @Override
    public Product update(Long id, CreateProductDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setStock(dto.stock());
        product.setCategory(category);

        return productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NoSuchElementException("Le produit à supprimer n'existe pas");
        }
        productRepository.deleteById(id);
    }


    @Override
    public void attachToCategory(Long productId, Long categoryId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Produit non trouvé"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Catégorie non trouvée"));

        product.setCategory(category);
        productRepository.save(product);
    }

    @Override
    public void detachFromCategory(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Produit non trouvé"));

        product.setCategory(null);
        productRepository.save(product);
    }

    // utiliser pour les tests Swagger
    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }
}
