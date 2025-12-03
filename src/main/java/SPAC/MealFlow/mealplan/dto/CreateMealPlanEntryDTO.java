package SPAC.MealFlow.mealplan.dto;

import SPAC.MealFlow.mealplan.model.MealPlanEntries;

import java.time.DayOfWeek;

public record CreateMealPlanEntryDTO(
        int recipeId,
        int servingsOverride,
        MealPlanEntries.MealType mealType,
        DayOfWeek dayOfWeek

) { }
