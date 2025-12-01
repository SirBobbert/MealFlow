package SPAC.MealFlow.mealplan.dto;

import java.util.List;

public record CreateMealPlanRequestDTO(
        String name,
        List<CreateMealPlanEntryDTO> listOfEntries
) { }
