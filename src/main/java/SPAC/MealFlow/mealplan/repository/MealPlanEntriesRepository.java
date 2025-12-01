package SPAC.MealFlow.mealplan.repository;

import SPAC.MealFlow.mealplan.model.MealPlanEntries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealPlanEntriesRepository extends JpaRepository<MealPlanEntries, Integer> {
}
