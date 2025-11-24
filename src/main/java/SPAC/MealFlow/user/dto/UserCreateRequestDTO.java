package SPAC.MealFlow.user.dto;

public record UserCreateRequestDTO(
        String name,
        String email,
        String password
) { }
