package SPAC.MealFlow.recipe.dto;

import java.util.Date;
import java.util.List;

public record CreateRecipeResponseDTO(
        int userId,
        String title,
        String description,
        String instructions,
        int servings,
        int prepTime,
        Date createdAt,
        List<CreateRecipeIngredientResponseDTO> ingredients
) {
}
