package SPAC.MealFlow.recipe.dto;

public record CreateRecipeIngredientResponseDTO(
        int ingredientId,
        String ingredientName,
        int amount,
        String unit
) {
}
