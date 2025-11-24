package SPAC.MealFlow.model;

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

    // meal plan this entry belongs to
    @ManyToOne
    @JoinColumn(name = "meal_plan_id")
    private MealPlans mealPlan;

    // recipe used for this entry
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    private Date createdAt;

    // type of meal
    @Enumerated(EnumType.STRING)
    private MealType mealType;

    // override servings for this entry, if needed
    private int servingsOverride;

    public enum MealType {
        BREAKFAST,
        LUNCH,
        DINNER,
        SNACK
    }
}
