package SPAC.MealFlow.recipe.dto;

public record CreateRecipeIngredientRequestDTO(
        int ingredientId,
        int amount,
        String unit
) {
}
