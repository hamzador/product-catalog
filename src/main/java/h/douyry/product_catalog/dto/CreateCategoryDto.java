package h.douyry.product_catalog.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryDto(
        @NotBlank(message = "Le nom est obligatoire")
        String name,
        @NotBlank(message = "La description est obligatoire")
        String description, Long parentCategoryId) {}