package SPAC.MealFlow.recipe.dto;

import java.util.List;

public record GetAllRecipesRequestDTO(
        int id,
        String title,
        String description,
        int servings,
        int prepTime,
        String instructions,

        // List of ingredients for this recipe
        List<CreateRecipeIngredientResponseDTO> ingredients
) { }

