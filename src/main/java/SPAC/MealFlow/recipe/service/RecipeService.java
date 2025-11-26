package SPAC.MealFlow.recipe.service;

import SPAC.MealFlow.common.exceptions.IngredientNotFoundException;
import SPAC.MealFlow.recipe.model.Recipe;
import SPAC.MealFlow.recipe.model.RecipeIngredient;
import SPAC.MealFlow.recipe.repository.IngredientRepository;
import SPAC.MealFlow.recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public Recipe createRecipe(Recipe recipe) {

        // check if ingredients exist
        for (RecipeIngredient ri : recipe.getRecipeIngredients()) {

            // get the FK to ingredient
            int ingredientId = ri.getIngredient().getId();

            // if ingredient does NOT exist
            if (!ingredientRepository.existsById(ingredientId)) {
                throw new IngredientNotFoundException("Ingredient with the ID " + ingredientId + " does not exist");
            }
        }

        return recipeRepository.save(recipe);
    }
}