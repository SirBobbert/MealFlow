package SPAC.MealFlow.recipe.model;

import SPAC.MealFlow.mealplan.model.MealPlanEntries;
import SPAC.MealFlow.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
public class Recipe {

    // primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private int id;

    // owner of this recipe
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // recipe title
    @ToString.Include
    private String title;

    // short description
    private String description;

    // full instructions text
    private String instructions;

    // default servings for this recipe
    private int servings;

    // preparation time in minutes
    private int prepTime;

    // creation timestamp
    private Date createdAt;

    // last update timestamp
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
