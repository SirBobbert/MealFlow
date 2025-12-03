package SPAC.MealFlow.mealplan.dto;

import SPAC.MealFlow.mealplan.model.MealPlanEntries;

import java.time.DayOfWeek;

public record CreateMealPlanEntryResponseDTO(
        int id,
        int recipeId,
        String recipeTitle,
        MealPlanEntries.MealType mealType,
        Integer servingsOverride,
        DayOfWeek dayOfWeek
) {
}
