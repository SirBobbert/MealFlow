package SPAC.MealFlow.recipe.dto;

import SPAC.MealFlow.recipe.model.RecipeIngredient;

import java.util.List;

public record RecipeCreateRequestDTO(
        String title,
        String description,
        String instructions,
        int servings,
        int prepTime,
        List<RecipeIngredientCreateRequestDTO> ingredients

        // TODO:
        // Add mealplan entries (look in Recipe.java class)
) {
}
