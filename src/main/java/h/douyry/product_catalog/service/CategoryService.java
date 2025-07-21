package h.douyry.product_catalog.service;

import h.douyry.product_catalog.dto.CreateCategoryDto;
import h.douyry.product_catalog.entity.Category;

import java.util.List;

public interface CategoryService {
    Category create(CreateCategoryDto dto);
    Category update(Long id, CreateCategoryDto dto);
    void delete(Long id);
    List<Category> getAll();
}
