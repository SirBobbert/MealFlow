package SPAC.MealFlow.common.dto;

import java.time.Instant;

public record ErrorResponseDTO(
        int status,
        String error,
        String message,
        String path,
        Instant timestamp
) {}