package SPAC.MealFlow.user.dto;

public record LoginResponseDTO(
        int id,
        String name,
        String email,
        String token
) { }
