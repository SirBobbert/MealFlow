package SPAC.MealFlow.auth.config;

import SPAC.MealFlow.auth.handler.RestAccessDeniedHandler;
import SPAC.MealFlow.auth.handler.RestAuthenticationEntryPoint;
import SPAC.MealFlow.auth.jwt.JwtAuthFilter;
import SPAC.MealFlow.auth.user.AuthUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class AuthSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthUserDetailsService userDetailsService;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final RestAccessDeniedHandler restAccessDeniedHandler;

    public AuthSecurityConfig(
            JwtAuthFilter jwtAuthFilter,
            AuthUserDetailsService userDetailsService,
            RestAuthenticationEntryPoint restAuthenticationEntryPoint,
            RestAccessDeniedHandler restAccessDeniedHandler
    ) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.restAccessDeniedHandler = restAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Enable CORS so frontend at http://localhost:5173 can call the API
        http.cors(Customizer.withDefaults());

        // Disable CSRF for token based API
        http.csrf(AbstractHttpConfigurer::disable);

        // Make the session stateless because we use JWT
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // Configure which endpoints are public and which require auth
        http.authorizeHttpRequests(auth -> auth
                // Login endpoint public (matches your Postman call)
                .requestMatchers(HttpMethod.POST, "/api/users/login").permitAll()

                // Example: user registration public (adjust to your real endpoints)
                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()

                // Everything under /api/users/** else requires auth
                // (this will include /api/users/{userId}/recipes if that is your mapping)
                .requestMatchers("/api/users/**").authenticated()


                .requestMatchers(HttpMethod.GET, "/api/ingredients/**")
                .hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/ingredients/**")
                .hasAnyRole("USER", "ADMIN")


                .requestMatchers(HttpMethod.POST, "/api/mealplan/**")
                .hasAnyRole("USER", "ADMIN")



                // Everything else requires authentication
                .anyRequest().authenticated()
        );

        // Use custom handlers for 401 and 403 so frontend gets a JSON body
        http.exceptionHandling(ex -> ex
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .accessDeniedHandler(restAccessDeniedHandler)
        );

        // Register JWT filter before the standard UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        // CORS config for Vite dev server
        CorsConfiguration config = new CorsConfiguration();

        // Allow cookies/authorization header
        config.setAllowCredentials(true);

        // Allowed origins (Vite dev server)
        config.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "http://127.0.0.1:5173"
        ));

        // Allowed HTTP methods
        config.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));

        // Allowed headers
        config.setAllowedHeaders(List.of(
                "Authorization", "Content-Type", "Accept"
        ));

        // Headers that the browser is allowed to read
        config.setExposedHeaders(List.of(
                "Authorization"
        ));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Apply this CORS config to all endpoints
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        // Password encoder used when creating users and checking passwords
        return new BCryptPasswordEncoder();
    }
}
