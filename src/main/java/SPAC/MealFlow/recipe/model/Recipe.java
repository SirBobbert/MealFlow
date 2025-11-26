package SPAC.MealFlow.recipe.model;

import SPAC.MealFlow.mealplan.model.MealPlanEntries;
import SPAC.MealFlow.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // owner of this recipe
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private String description;
    private String instructions;
    private int servings;
    private int prepTime; // minutes

    private Date createdAt;
    private Date updatedAt;

    // ingredients for this recipe
    @OneToMany(
            mappedBy = "recipe",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();

    // meal plan entries that use this recipe
    @OneToMany(mappedBy = "recipe")
    private List<MealPlanEntries> mealPlanEntries;
}
