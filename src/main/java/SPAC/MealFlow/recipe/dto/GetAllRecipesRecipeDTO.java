package SPAC.MealFlow.recipe.dto;

public record GetAllRecipesRecipeDTO(
        int id,
        String title,
        String description,
        int servings,
        int prepTime
) { }

