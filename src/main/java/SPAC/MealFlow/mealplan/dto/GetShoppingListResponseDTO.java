package SPAC.MealFlow.mealplan.dto;

import java.util.List;

public record GetShoppingListResponseDTO(
        List<ShoppingListItemDTO> items
) {
}
