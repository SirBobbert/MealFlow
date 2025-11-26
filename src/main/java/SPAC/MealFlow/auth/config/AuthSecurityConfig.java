package SPAC.MealFlow.auth.config;

import SPAC.MealFlow.auth.handler.RestAccessDeniedHandler;
import SPAC.MealFlow.auth.handler.RestAuthenticationEntryPoint;
import SPAC.MealFlow.auth.jwt.JwtAuthFilter;
import SPAC.MealFlow.auth.user.AuthUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

        // Disable CSRF for token based API
        http.csrf(AbstractHttpConfigurer::disable);

        // Make the session stateless because we use JWT
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // Configure which endpoints are public and which require auth
        http.authorizeHttpRequests(auth -> auth
                // Auth endpoints (login etc.) public
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()

                // User registration or similar public
                .requestMatchers("/api/users/**").permitAll()

                // Recipe endpoints protected
                .requestMatchers(HttpMethod.GET, "/api/recipes/**")
                .hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/recipes/**")
                .hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/recipes/**")
                .hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/recipes/**")
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
    public PasswordEncoder passwordEncoder() {

        // Password encoder used when creating users and checking passwords
        return new BCryptPasswordEncoder();
    }
}
