package SPAC.MealFlow.mealplan.repository;

import SPAC.MealFlow.mealplan.model.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MealPlanRepository extends JpaRepository<MealPlan, Integer> {
    @Query("""
            SELECT mp
            FROM MealPlan mp
            LEFT JOIN FETCH mp.entries e
            LEFT JOIN FETCH e.recipe r
            WHERE mp.id = :id
              AND mp.user.id = :userId
            """)
    Optional<MealPlan> findByIdAndUserIdWithEntriesAndRecipes(
            @Param("id") Integer id,
            @Param("userId") Integer userId
    );


    @Query("""
      SELECT DISTINCT mp
      FROM MealPlan mp
      LEFT JOIN FETCH mp.entries e
      LEFT JOIN FETCH e.recipe r
      WHERE mp.user.id = :userId
      """)
    List<MealPlan> findAllByUserIdWithEntriesAndRecipes(@Param("userId") Integer userId);
}
