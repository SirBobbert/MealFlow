package SPAC.MealFlow.mealplan.dto;

import java.util.List;

public record CreateMealPlanResponseDTO(
        int id,
        String name,
        int userId,
        String userName,
        List<CreateMealPlanEntryResponseDTO> entries
) {}
