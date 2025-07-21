package h.douyry.product_catalog.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateProductDto(
        @NotBlank(message = "Le nom est obligatoire")
        String name,
        @Min(value = 0, message = "Le prix ne peut pas être négatif")
        double price,
        @Min(value = 0, message = "Le stock ne peut pas être négatif")
        int stock,
        @NotNull(message = "L'identifiant de la catégorie est obligatoire")
        Long categoryId
) {}