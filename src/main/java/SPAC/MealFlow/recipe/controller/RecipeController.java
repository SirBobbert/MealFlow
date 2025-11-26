package SPAC.MealFlow.recipe.controller;

import SPAC.MealFlow.recipe.dto.RecipeCreateRequestDTO;
import SPAC.MealFlow.recipe.dto.RecipeIngredientResponseDTO;
import SPAC.MealFlow.recipe.dto.RecipeResponseDTO;
import SPAC.MealFlow.recipe.model.Ingredient;
import SPAC.MealFlow.recipe.model.Recipe;
import SPAC.MealFlow.recipe.model.RecipeIngredient;
import SPAC.MealFlow.recipe.service.IngredientService;
import SPAC.MealFlow.recipe.service.RecipeService;
import SPAC.MealFlow.auth.user.AuthUserDetails;
import SPAC.MealFlow.user.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public RecipeController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }


    @PostMapping
    public ResponseEntity<RecipeResponseDTO> createRecipe(@RequestBody RecipeCreateRequestDTO request) {

        // Get current authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AuthUserDetails principal = (AuthUserDetails) auth.getPrincipal();
        User currentUser = principal.getUser();

        // Build base recipe (without ingredients first)
        Recipe recipe = Recipe.builder()
                .user(currentUser)
                .title(request.title())
                .description(request.description())
                .instructions(request.instructions())
                .servings(request.servings())
                .prepTime(request.prepTime())
                .createdAt(new Date())
                .build();

        // Map DTO ingredients -> RecipeIngredient entities
        List<RecipeIngredient> recipeIngredients = request.ingredients().stream()
                .map(ingDto -> {
                    // Get existing Ingredient by id
                    Ingredient ingredient = ingredientService.getById(ingDto.ingredientId());

                    // Build join entity
                    return RecipeIngredient.builder()
                            .recipe(recipe)
                            .ingredient(ingredient)
                            .amount(ingDto.amount())
                            .unit(ingDto.unit())
                            .build();
                })
                .toList();

        // Attach to recipe
        recipe.setRecipeIngredients(recipeIngredients);

        // Persist
        Recipe created = recipeService.createRecipe(recipe);

        // Map entity -> response DTO
        List<RecipeIngredientResponseDTO> ingredientResponses = created.getRecipeIngredients().stream()
                .map(ri -> new RecipeIngredientResponseDTO(
                        ri.getIngredient().getId(),
                        ri.getIngredient().getName(),
                        ri.getAmount(),
                        ri.getUnit()
                ))
                .toList();

        RecipeResponseDTO response = new RecipeResponseDTO(
                created.getId(),
                created.getUser().getId(),
                created.getTitle(),
                created.getDescription(),
                created.getInstructions(),
                created.getServings(),
                created.getPrepTime(),
                created.getCreatedAt(),
                ingredientResponses
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

}
