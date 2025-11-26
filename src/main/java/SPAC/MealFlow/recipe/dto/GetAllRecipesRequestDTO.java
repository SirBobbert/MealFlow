package SPAC.MealFlow.recipe.dto;

public record GetAllRecipesRequestDTO(
        int id,
        String title,
        String description,
        int servings,
        int prepTime,
        String instructions
) { }

