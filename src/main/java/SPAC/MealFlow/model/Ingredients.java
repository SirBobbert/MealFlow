package SPAC.MealFlow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Ingredients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    // category for this ingredient
    @Enumerated(EnumType.STRING)
    private Category category;

    // links to recipes that use this ingredient
    @OneToMany(mappedBy = "ingredient")
    private List<RecipeIngredients> recipeIngredients;

    // links to shopping list items using this ingredient
    @OneToMany(mappedBy = "ingredient")
    private List<ShoppingListItems> shoppingListItems;

    public enum Category {
        VEGETABLE,
        FRUIT,
        GRAIN,
        PROTEIN,
        DAIRY,
        SPICE,
        OTHER
    }
}
