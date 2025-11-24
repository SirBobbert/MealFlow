package SPAC.fullstack.model;

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

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MealPlans {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Owner of this meal plan
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    // All entries (days/meals) in this plan
    @OneToMany(mappedBy = "mealPlan")
    private List<MealPlanEntries> entries;

    // All shopping list items generated from this plan
    @OneToMany(mappedBy = "mealPlan")
    private List<ShoppingListItems> shoppingListItems;
}
