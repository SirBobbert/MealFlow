package SPAC.MealFlow.recipe.controller;

import SPAC.MealFlow.recipe.dto.IngredientCreateRequestDTO;
import SPAC.MealFlow.recipe.dto.IngredientResponseDTO;
import SPAC.MealFlow.recipe.model.Ingredient;
import SPAC.MealFlow.recipe.repository.IngredientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {

    private final IngredientRepository ingredientRepository;

    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    // Get all ingredients (for dropdown in frontend)
    @GetMapping
    public List<IngredientResponseDTO> getAllIngredients() {
        return ingredientRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    // Create ingredient if it does not already exist by name
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IngredientResponseDTO createIngredient(@RequestBody IngredientCreateRequestDTO request) {

        Ingredient ingredient = ingredientRepository
                .findByNameIgnoreCase(request.name())
                .orElseGet(() -> {
                    Ingredient newIng = new Ingredient();
                    newIng.setName(request.name());
                    newIng.setCategory(Ingredient.Category.valueOf(request.category() != null
                            ? request.category()
                            : "OTHER"));
                    return ingredientRepository.save(newIng);
                });

        return toDto(ingredient);
    }

    private IngredientResponseDTO toDto(Ingredient ingredient) {
        return new IngredientResponseDTO(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getCategory() != null ? ingredient.getCategory().toString() : null
        );
    }
}
