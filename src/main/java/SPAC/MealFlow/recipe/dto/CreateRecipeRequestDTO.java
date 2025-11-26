package SPAC.MealFlow.recipe.dto;

import java.util.List;

public record CreateRecipeRequestDTO(
        String title,
        String description,
        String instructions,
        int servings,
        int prepTime,
        List<CreateRecipeIngredientRequestDTO> ingredients
) {
}
