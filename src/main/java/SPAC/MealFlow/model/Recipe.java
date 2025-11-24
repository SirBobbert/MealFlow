package SPAC.MealFlow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @OneToMany(mappedBy = "recipe")
    private List<RecipeIngredients> recipeIngredients;

    // meal plan entries that use this recipe
    @OneToMany(mappedBy = "recipe")
    private List<MealPlanEntries> mealPlanEntries;
}
