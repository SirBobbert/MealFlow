// src/main/java/SPAC/MealFlow/security/CustomAuthenticationEntryPoint.java
package SPAC.MealFlow.auth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;
import SPAC.MealFlow.common.dto.ErrorResponseDTO;

import java.io.IOException;
import java.time.Instant;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        // Debug log so you can see if this is hit
        System.out.println(">>> CustomAuthenticationEntryPoint called");

        ErrorResponseDTO body = new ErrorResponseDTO(
                HttpServletResponse.SC_UNAUTHORIZED,
                "Unauthorized",
                "Access denied",
                request.getRequestURI(),
                Instant.now()
        );

        // Set status code
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // Set content type
        response.setContentType("application/json");
        // Set charset
        response.setCharacterEncoding("UTF-8");
        // Write body
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
