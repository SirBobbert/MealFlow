package SPAC.MealFlow.recipe.dto;

import SPAC.MealFlow.recipe.model.RecipeIngredients;

import java.util.List;

public record RecipeCreateRequestDTO(
        String title,
        String description,
        String instructions,
        int servings,
        int prepTime,
        List<RecipeIngredients> listOfIngredients

        // TODO:
        // Add mealplan entries (look in Recipe.java class)
) {
}
