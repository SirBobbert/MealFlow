package SPAC.MealFlow.security;

import SPAC.MealFlow.user.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    // Underlying User entity
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    // Expose User for controllers/services if needed
    public User getUser() {
        return user;
    }

    // Map user role to Spring authority
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roleName = "ROLE_" + user.getRole().name();
        return List.of(new SimpleGrantedAuthority(roleName));
    }

    // Return encoded password
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // Username is user's email
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
