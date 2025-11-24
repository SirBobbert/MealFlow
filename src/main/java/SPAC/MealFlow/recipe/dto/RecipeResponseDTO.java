package SPAC.MealFlow.recipe.dto;

import SPAC.MealFlow.recipe.model.RecipeIngredients;

import java.util.Date;
import java.util.List;

public record RecipeResponseDTO(
        int id,
        int userId,
        String title,
        String description,
        String instructions,
        int servings,
        int prepTime,
        Date createdAt,
        List<RecipeIngredients> listOfIngredients
) { }
