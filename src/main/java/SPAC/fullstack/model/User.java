package SPAC.fullstack.model;

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

    // Store hashed password here
    private String password;

    private Date createdAt;

    // Role for this user
    @Enumerated(EnumType.STRING)
    private Role role;

    // All recipes owned by this user
    @OneToMany(mappedBy = "user")
    private List<Recipe> recipes;

    // All meal plans owned by this user
    @OneToMany(mappedBy = "user")
    private List<MealPlans> mealPlans;

    public enum Role {
        ADMIN,
        USER
    }
}
