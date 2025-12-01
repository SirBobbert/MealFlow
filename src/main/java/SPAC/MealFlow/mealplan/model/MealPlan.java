package SPAC.MealFlow.mealplan.model;

import SPAC.MealFlow.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MealPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // owner of this meal plan
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    // all entries (days/meals) in this plan
    @OneToMany(
            mappedBy = "mealPlan",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<MealPlanEntries> entries = new ArrayList<>();

    // all shopping list items generated from this plan
    @OneToMany(
            mappedBy = "mealPlan",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<ShoppingListItems> shoppingListItems = new ArrayList<>();

    // helper to keep both sides in sync
    public void addEntry(MealPlanEntries entry) {
        // ensure list is never null (extra safety)
        if (entries == null) {
            entries = new ArrayList<>();
        }
        entries.add(entry);
        entry.setMealPlan(this);
    }

}
