package SPAC.MealFlow.mealplan.model;

import SPAC.MealFlow.recipe.model.Ingredient;
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
public class ShoppingListItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // meal plan this item belongs to
    @ManyToOne
    @JoinColumn(name = "meal_plan_id")
    private MealPlans mealPlan;

    // ingredient on this shopping list line
    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    private int amount;
    private String unit;
    private boolean checked;
}
