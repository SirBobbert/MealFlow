package SPAC.fullstack.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MealPlanEntries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Meal plan this entry belongs to
    @ManyToOne
    @JoinColumn(name = "meal_plan_id")
    private MealPlans mealPlan;

    // Recipe used for this entry
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    private Date createdAt;

    // Type of meal
    @Enumerated(EnumType.STRING)
    private MealType mealType;

    // Override servings for this entry, if needed
    private int servingsOverride;

    public enum MealType {
        BREAKFAST,
        LUNCH,
        DINNER,
        SNACK
    }
}
