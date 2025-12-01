package SPAC.MealFlow.mealplan.repository;

import SPAC.MealFlow.mealplan.model.ShoppingListItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingListItemsRepository extends JpaRepository<ShoppingListItems, Integer> {

    // find all items for a given mealplan
    List<ShoppingListItems> findByMealPlanId(int mealPlanId);
}
