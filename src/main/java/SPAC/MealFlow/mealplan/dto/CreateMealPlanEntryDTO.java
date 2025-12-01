package SPAC.MealFlow.mealplan.dto;

import SPAC.MealFlow.mealplan.model.MealPlanEntries;

public record CreateMealPlanEntryDTO(
        int recipeId,
        int servingsOverride,
        MealPlanEntries.MealType mealType
) { }
