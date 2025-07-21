package h.douyry.product_catalog.service.impl;

import h.douyry.product_catalog.dto.CreateCategoryDto;
import h.douyry.product_catalog.entity.Category;
import h.douyry.product_catalog.repository.CategoryRepository;
import h.douyry.product_catalog.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(CreateCategoryDto dto) {
        Category category = new Category();
        category.setName(dto.name());
        category.setDescription(dto.description());

        if (dto.parentCategoryId() != null) {
            Category parent = categoryRepository.findById(dto.parentCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent category not found"));
            category.setParentCategory(parent);
        }

        return categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, CreateCategoryDto dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category not found"));

        category.setName(dto.name());
        category.setDescription(dto.description());

        if (dto.parentCategoryId() != null) {
            Category parent = categoryRepository.findById(dto.parentCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent category not found"));
            category.setParentCategory(parent);
        } else {
            category.setParentCategory(null);
        }

        return categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new NoSuchElementException("La catégorie à supprimer n'existe pas");
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}
