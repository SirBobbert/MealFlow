package SPAC.MealFlow.recipe.dto;

import java.util.List;

public record GetAllRecipesResponseDTO(
        List<GetAllRecipesRecipeDTO> recipes) { }

