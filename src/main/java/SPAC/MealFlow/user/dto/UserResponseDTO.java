package SPAC.MealFlow.user.dto;

import java.util.Date;

public record UserResponseDTO(
        int id,
        String name,
        String email,
        String role,
        Date createdAt
) { }
