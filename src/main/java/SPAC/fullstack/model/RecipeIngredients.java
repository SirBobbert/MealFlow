package SPAC.fullstack.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RecipeIngredients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Recipe using this ingredient
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    // Ingredient used in the recipe
    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredients ingredient;

    private int amount;
    private String unit;
}
