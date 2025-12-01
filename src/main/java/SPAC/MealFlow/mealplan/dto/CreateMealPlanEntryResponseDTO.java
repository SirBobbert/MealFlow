package SPAC.MealFlow.mealplan.dto;

import SPAC.MealFlow.mealplan.model.MealPlanEntries;

public record CreateMealPlanEntryResponseDTO(
        int id,
        int recipeId,
        String recipeTitle,
        MealPlanEntries.MealType mealType,
        Integer servingsOverride
) {
}
