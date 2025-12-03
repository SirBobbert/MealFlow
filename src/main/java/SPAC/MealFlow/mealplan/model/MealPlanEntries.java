package SPAC.MealFlow.mealplan.model;

import SPAC.MealFlow.recipe.model.Recipe;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.util.Date;

@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
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
    private MealPlan mealPlan;

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

    // which day of week this entry is for
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    public enum MealType {
        BREAKFAST,
        LUNCH,
        DINNER,
        SNACK
    }


}
