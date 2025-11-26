package SPAC.MealFlow.recipe.repository;

import SPAC.MealFlow.recipe.dto.GetAllRecipesResponseDTO;
import SPAC.MealFlow.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    // get all recipes for a specific user
    List<Recipe> findAllByUserId(int userId);
}
