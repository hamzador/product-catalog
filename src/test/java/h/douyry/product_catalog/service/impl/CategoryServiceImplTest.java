package h.douyry.product_catalog.service.impl;

import h.douyry.product_catalog.dto.CreateCategoryDto;
import h.douyry.product_catalog.entity.Category;
import h.douyry.product_catalog.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void createCategory_withoutParent_success() {
        CreateCategoryDto dto = new CreateCategoryDto("Chaussures", "Tous types de chaussures", null);
        Category saved = new Category();
        saved.setId(1L);
        saved.setName("Chaussures");
        saved.setDescription("Tous types de chaussures");

        when(categoryRepository.save(any(Category.class))).thenReturn(saved);

        Category result = categoryService.create(dto);

        assertEquals("Chaussures", result.getName());
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void createCategory_withInvalidParent_throwsException() {
        CreateCategoryDto dto = new CreateCategoryDto("Sneakers", "Sous catégorie", 999L);

        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> categoryService.create(dto));
    }

    @Test
    void deleteCategory_notFound_throwsException() {
        when(categoryRepository.existsById(5L)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> categoryService.delete(5L));
    }
}