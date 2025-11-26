package SPAC.MealFlow.recipe.service;

import SPAC.MealFlow.common.exceptions.IngredientNotFoundException;
import SPAC.MealFlow.common.exceptions.RecipeNotFoundException;
import SPAC.MealFlow.recipe.dto.GetAllRecipesRecipeDTO;
import SPAC.MealFlow.recipe.dto.RecipeCreateRequestDTO;
import SPAC.MealFlow.recipe.model.Ingredient;
import SPAC.MealFlow.recipe.model.Recipe;
import SPAC.MealFlow.recipe.model.RecipeIngredient;
import SPAC.MealFlow.recipe.repository.IngredientRepository;
import SPAC.MealFlow.recipe.repository.RecipeRepository;
import SPAC.MealFlow.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<GetAllRecipesRecipeDTO> getAllUserRecipes(int userId) {

        // load all Recipe entities for this user
        List<Recipe> recipes = recipeRepository.findAllByUserId(userId);

        // map entities to DTOs
        return recipes.stream()
                .map(r -> new GetAllRecipesRecipeDTO(
                        r.getId(),
                        r.getTitle(),
                        r.getDescription(),
                        r.getServings(),
                        r.getPrepTime()

                ))
                .toList();
    }

    @Transactional
    public Recipe updateRecipe(int id, User currentUser, RecipeCreateRequestDTO request) {

        // Load existing recipe
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe with ID " + id + " not found"));

        // Optional: security check - only owner can update
        if (recipe.getUser().getId() != currentUser.getId()) {
            throw new RecipeNotFoundException("Recipe with ID " + id + " not found for this user");
        }

        // Update scalar fields
        recipe.setTitle(request.title());
        recipe.setDescription(request.description());
        recipe.setInstructions(request.instructions());
        recipe.setServings(request.servings());
        recipe.setPrepTime(request.prepTime());

        // Clear current ingredients (this list MUST be mutable)
        recipe.getRecipeIngredients().clear();

        // Rebuild ingredients
        List<RecipeIngredient> newIngredients = request.ingredients().stream()
                .map(ingDto -> {
                    Ingredient ingredient = ingredientRepository.getById(ingDto.ingredientId());

                    return RecipeIngredient.builder()
                            .recipe(recipe)                 // link back to recipe
                            .ingredient(ingredient)
                            .amount(ingDto.amount())
                            .unit(ingDto.unit())
                            .build();
                })
                .collect(Collectors.toCollection(ArrayList::new));

        // Attach new ingredients
        recipe.getRecipeIngredients().addAll(newIngredients);

        // Persist
        return recipeRepository.save(recipe);
    }

}