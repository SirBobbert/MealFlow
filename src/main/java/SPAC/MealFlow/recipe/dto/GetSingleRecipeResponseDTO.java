package SPAC.MealFlow.recipe.dto;

import java.util.List;

public record GetSingleRecipeResponseDTO(

        int id,
        String title,
        String description,
        int servings,
        int prepTime,
        String instructions,
        List<CreateRecipeIngredientResponseDTO> ingredients
) {
}
