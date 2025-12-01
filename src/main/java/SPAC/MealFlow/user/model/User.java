package SPAC.MealFlow.user.model;

import SPAC.MealFlow.mealplan.model.MealPlan;
import SPAC.MealFlow.recipe.model.Recipe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(
        name = "user",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_user_email", columnNames = "email")
        }
)
public class User {

    // primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private int id;

    // display name
    @ToString.Include
    private String name;

    // unique email identifier
    @Column(nullable = false, unique = true)
    @ToString.Include
    private String email;

    // hashed password
    private String password;

    // creation timestamp
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
