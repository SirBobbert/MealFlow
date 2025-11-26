package SPAC.MealFlow.recipe.dto;

public record GetSingleRecipeResponseDTO(
        int id,
        String title,
        String description,
        int servings,
        int prepTime,
        String instructions
) { }
