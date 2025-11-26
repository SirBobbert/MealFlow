package SPAC.MealFlow.recipe.controller;

import SPAC.MealFlow.recipe.dto.RecipeCreateRequestDTO;
import SPAC.MealFlow.recipe.dto.RecipeResponseDTO;
import SPAC.MealFlow.recipe.model.Recipe;
import SPAC.MealFlow.recipe.service.RecipeService;
import SPAC.MealFlow.auth.user.AuthUserDetails;
import SPAC.MealFlow.user.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    public ResponseEntity<RecipeResponseDTO> createRecipe(@RequestBody RecipeCreateRequestDTO request) {

        // Get current authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AuthUserDetails principal = (AuthUserDetails) auth.getPrincipal();
        User currentUser = principal.getUser();

        // Build entity from DTO + current user
        Recipe recipe = Recipe.builder()
                .user(currentUser)
                .title(request.title())
                .description(request.description())
                .instructions(request.instructions())
                .servings(request.servings())
                .prepTime(request.prepTime())
                .createdAt(new Date())
                .recipeIngredients(request.listOfIngredients())
                .build();

        Recipe created = recipeService.createRecipe(recipe);

        RecipeResponseDTO response = new RecipeResponseDTO(
                created.getId(),
                created.getUser().getId(),
                created.getTitle(),
                created.getDescription(),
                created.getInstructions(),
                created.getServings(),
                created.getPrepTime(),
                created.getCreatedAt(),
                created.getRecipeIngredients()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
