package SPAC.MealFlow.auth.jwt;

import SPAC.MealFlow.auth.user.AuthUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    // Service for parsing and validating JWT
    private final JwtTokenService jwtService;

    // Service for loading users by email/username
    private final AuthUserDetailsService userDetailsService;

    public JwtAuthFilter(JwtTokenService jwtService,
                         AuthUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Read Authorization header
        String authHeader = request.getHeader("Authorization");

        // If no Bearer token - just continue, do not block the request
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the token part
        String jwt = authHeader.substring(7);

        try {
            // Extract username/email from token
            String username = jwtService.extractUsername(jwt);

            // Only set authentication if none is present yet
            if (username != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                // Load user details from DB
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Validate token against user details
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    // Put authenticated user into SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

        } catch (Exception e) {
            // Any problem with token -> clear context and continue (public endpoints still allowed)
            SecurityContextHolder.clearContext();
        }

        // Continue filter chain
        filterChain.doFilter(request, response);
    }
}
