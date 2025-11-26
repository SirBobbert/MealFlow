package SPAC.MealFlow.recipe.dto;

public record RecipeIngredientCreateRequestDTO(
        int ingredientId,
        int amount,
        String unit
) {
}
