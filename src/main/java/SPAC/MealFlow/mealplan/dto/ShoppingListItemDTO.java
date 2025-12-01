package SPAC.MealFlow.mealplan.dto;

import SPAC.MealFlow.recipe.model.Ingredient;

public record ShoppingListItemDTO(
        int ingredientId,
        String ingredientName,
        Ingredient.Category category,
        double amount,
        String unit,
        boolean checked
) {}
