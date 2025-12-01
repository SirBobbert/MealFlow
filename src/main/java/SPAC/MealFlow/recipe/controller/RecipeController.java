package SPAC.MealFlow.recipe.controller;

import SPAC.MealFlow.recipe.dto.*;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> createRecipe(@RequestBody CreateRecipeRequestDTO request) {

        // get current authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AuthUserDetails principal = (AuthUserDetails) auth.getPrincipal();
        User currentUser = principal.getUser();

        // build base recipe
        Recipe recipe = Recipe.builder()
                .user(currentUser)
                .title(request.title())
                .description(request.description())
                .instructions(request.instructions())
                .servings(request.servings())
                .prepTime(request.prepTime())
                .createdAt(new Date())
                .build();

        // map DTO ingredients
        List<RecipeIngredient> recipeIngredients = request.ingredients().stream()
                .map(ingDto -> {

                    // get existing Ingredient by id
                    Ingredient ingredient = ingredientService.getById(ingDto.ingredientId());

                    return RecipeIngredient.builder()
                            .recipe(recipe)
                            .ingredient(ingredient)
                            .amount(ingDto.amount())
                            .unit(ingDto.unit())
                            .build();
                })
                .collect(Collectors.toCollection(ArrayList::new));

        recipe.setRecipeIngredients(recipeIngredients);

        Recipe created = recipeService.createRecipe(recipe);

        // map entity to response DTO
        List<CreateRecipeIngredientResponseDTO> ingredientResponses = created.getRecipeIngredients().stream()
                .map(ri -> new CreateRecipeIngredientResponseDTO(
                        ri.getIngredient().getId(),
                        ri.getIngredient().getName(),
                        ri.getAmount(),
                        ri.getUnit()
                ))
                .toList();

        CreateRecipeResponseDTO response = new CreateRecipeResponseDTO(
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipeById(@PathVariable int id) {

        GetSingleRecipeResponseDTO recipe = recipeService.getRecipeById(id);

        GetSingleRecipeResponseDTO response = new GetSingleRecipeResponseDTO(
                recipe.id(),
                recipe.title(),
                recipe.description(),
                recipe.servings(),
                recipe.prepTime(),
                recipe.instructions(),
                recipe.ingredients()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllRecipes() {

        // get current authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AuthUserDetails principal = (AuthUserDetails) auth.getPrincipal();
        User currentUser = principal.getUser();

        List<GetAllRecipesRequestDTO> recipes =
                recipeService.getAllUserRecipes(currentUser.getId());

        GetAllRecipesResponseDTO response = new GetAllRecipesResponseDTO(recipes);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> updateRecipe(
            @PathVariable int id,
            @RequestBody CreateRecipeRequestDTO request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AuthUserDetails principal = (AuthUserDetails) auth.getPrincipal();
        User currentUser = principal.getUser();

        Recipe updated = recipeService.updateRecipe(id, currentUser, request);

        List<CreateRecipeIngredientResponseDTO> ingredientResponses =
                updated.getRecipeIngredients().stream()
                        .map(ri -> new CreateRecipeIngredientResponseDTO(
                                ri.getIngredient().getId(),
                                ri.getIngredient().getName(),
                                ri.getAmount(),
                                ri.getUnit()
                        ))
                        .toList(); 

        CreateRecipeResponseDTO response = new CreateRecipeResponseDTO(
                updated.getId(),
                updated.getUser().getId(),
                updated.getTitle(),
                updated.getDescription(),
                updated.getInstructions(),
                updated.getServings(),
                updated.getPrepTime(),
                updated.getCreatedAt(),
                ingredientResponses
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

        @DeleteMapping("/{id}")
        public ResponseEntity<?> deleteRecipe(@PathVariable int id) {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            AuthUserDetails principal = (AuthUserDetails) auth.getPrincipal();
            User currentUser = principal.getUser();

            Recipe deleted = recipeService.deleteRecipe(id, currentUser);

            DeleteRecipeResponseDTO response = new DeleteRecipeResponseDTO(
                    deleted.getId(),
                    deleted.getTitle(),
                    "Recipe was deleted successfully"
            );

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }


}
