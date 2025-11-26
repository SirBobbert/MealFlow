package SPAC.MealFlow.user.model;

import SPAC.MealFlow.mealplan.model.MealPlan;
import SPAC.MealFlow.recipe.model.Recipe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "user",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_user_email", columnNames = "email")
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    @Column(nullable = false, unique = true)
    private String email;

    // store hashed password here
    private String password;

    private Date createdAt;

    // role for this user
    @Enumerated(EnumType.STRING)
    private Role role;

    // all recipes owned by this user
    @OneToMany(mappedBy = "user")
    private List<Recipe> recipes;

    // all meal plans owned by this user
    @OneToMany(mappedBy = "user")
    private List<MealPlan> mealPlans;

    public enum Role {
        ADMIN,
        USER
    }
}
