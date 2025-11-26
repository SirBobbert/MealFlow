package SPAC.MealFlow.auth.config;

import SPAC.MealFlow.auth.handler.RestAccessDeniedHandler;
import SPAC.MealFlow.auth.handler.RestAuthenticationEntryPoint;
import SPAC.MealFlow.auth.user.AuthUserDetailsService;
import SPAC.MealFlow.auth.jwt.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// Import for HttpSecurity configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// Import for disabling CSRF with lambda
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// Import for stateless session policy
import org.springframework.security.config.http.SessionCreationPolicy;
// Import for password encoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
// Import for filter chain
import org.springframework.security.web.SecurityFilterChain;
// Import to register JWT filter before username/password auth
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class AuthSecurityConfig {

    // Filter that reads JWT from Authorization header and sets SecurityContext
    private final JwtAuthFilter jwtAuthFilter;

    // Service used by JwtAuthenticationFilter to load user details from DB
    private final AuthUserDetailsService userDetailsService;

    // Custom entry point for 401 Unauthorized responses
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    // Custom handler for 403 Forbidden responses
    private final RestAccessDeniedHandler restAccessDeniedHandler;

    public AuthSecurityConfig(JwtAuthFilter jwtAuthFilter,
                              AuthUserDetailsService userDetailsService,
                              RestAuthenticationEntryPoint restAuthenticationEntryPoint,
                              RestAccessDeniedHandler restAccessDeniedHandler) {
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
                .requestMatchers("/api/users/**").permitAll()
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
