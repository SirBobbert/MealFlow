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

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
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
    private List<MealPlans> mealPlans;

    public enum Role {
        ADMIN,
        USER
    }
}
