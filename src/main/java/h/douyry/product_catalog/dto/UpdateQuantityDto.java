package h.douyry.product_catalog.dto;

import jakarta.validation.constraints.Min;

public record UpdateQuantityDto(
        @Min(value = 1, message = "La quantité doit être au moins 1")
        int quantity
) { }
