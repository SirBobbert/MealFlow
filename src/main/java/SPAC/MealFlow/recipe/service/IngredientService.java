package SPAC.MealFlow.recipe.service;

import SPAC.MealFlow.recipe.model.Ingredient;
import SPAC.MealFlow.recipe.model.Recipe;
import SPAC.MealFlow.recipe.repository.IngredientRepository;
import SPAC.MealFlow.recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Ingredient getById(int id) {
        return ingredientRepository.getById(id);
    }

}
