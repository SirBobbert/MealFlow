package SPAC.MealFlow.user.dto;

import java.util.Date;

public record LoginResponseDTO(
        int id,
        String name,
        String email
) { }
