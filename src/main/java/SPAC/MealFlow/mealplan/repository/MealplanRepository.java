package SPAC.MealFlow.mealplan.repository;

import SPAC.MealFlow.mealplan.model.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealplanRepository extends JpaRepository<MealPlan, Integer> {
}
