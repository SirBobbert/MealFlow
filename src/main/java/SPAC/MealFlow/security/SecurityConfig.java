package SPAC.MealFlow.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    // Filter that reads JWT from Authorization header
    private final JwtAuthenticationFilter jwtAuthFilter;

    // Needed only so Spring knows how to build CustomUserDetails etc. via JwtAuthenticationFilter
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter,
                          CustomUserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Disable CSRF (we use JWT, stateless)
        http.csrf(AbstractHttpConfigurer::disable);

        // Stateless session - no HttpSession auth
        http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Route authorization rules
        http.authorizeHttpRequests(auth -> auth
                // Allow registration and login without token
                .requestMatchers(
                        "/api/users/create",
                        "/api/users/login"
                ).permitAll()
                // Everything else requires valid JWT set by JwtAuthenticationFilter
                .anyRequest().authenticated()
        );

        // Register JWT filter before UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        // Basic HTTP config (not really used, but harmless)
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Password encoder used in UserService.createUser and authenticate()
        return new BCryptPasswordEncoder();
    }
}
