package SPAC.MealFlow.recipe.dto;

public record RecipeIngredientResponseDTO(
        int ingredientId,
        String ingredientName,
        int amount,
        String unit
) {
}
