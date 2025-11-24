package SPAC.MealFlow.recipe.controller;

import SPAC.MealFlow.recipe.dto.RecipeCreateRequestDTO;
import SPAC.MealFlow.recipe.model.Recipe;
import SPAC.MealFlow.recipe.service.RecipeService;
import SPAC.MealFlow.recipe.dto.RecipeResponseDTO;
import SPAC.MealFlow.user.model.User;
import SPAC.MealFlow.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/users/{userId}/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    private final UserService userService;


    @Autowired
    public RecipeController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRecipe(@PathVariable int userId, @RequestBody RecipeCreateRequestDTO request) {

        // find owner from DB
        User user = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Recipe recipe = Recipe.builder()
                .user(user)
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
