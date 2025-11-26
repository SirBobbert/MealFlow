package SPAC.MealFlow.common;

import SPAC.MealFlow.common.dto.ErrorResponseDTO;
import SPAC.MealFlow.common.exceptions.IngredientNotFoundException;
import SPAC.MealFlow.common.exceptions.InvalidCredentialsException;
import SPAC.MealFlow.common.exceptions.RecipeNotFoundException;
import SPAC.MealFlow.common.exceptions.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponseDTO> buildError(
            HttpStatus status, String error, String message, String path) {

        return ResponseEntity.status(status).body(
                new ErrorResponseDTO(
                        status.value(),
                        error,
                        message,
                        path,
                        Instant.now()
                )
        );
    }

    @ExceptionHandler(IngredientNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleIngredientExist(
            IngredientNotFoundException ex, HttpServletRequest req) {
        return buildError(HttpStatus.NOT_FOUND, "Ingredient doesn't exist",
                ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserExists(
            UserAlreadyExistsException ex, HttpServletRequest req) {
        return buildError(HttpStatus.CONFLICT, "User already exists",
                ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidCredentials(
            InvalidCredentialsException ex, HttpServletRequest req) {
        return buildError(HttpStatus.UNAUTHORIZED, "Invalid credentials",
                ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleRecipeNotFound(
            RecipeNotFoundException ex, HttpServletRequest req) {
        return buildError(HttpStatus.NOT_FOUND, "Recipe not found",
                ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidation(
            MethodArgumentNotValidException ex, HttpServletRequest req) {
        String message = ex.getBindingResult().getFieldError().getDefaultMessage();
        return buildError(HttpStatus.BAD_REQUEST, "Validation failed",
                message, req.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneric(
            Exception ex, HttpServletRequest req) {

        // Log full stack trace so you can see what actually broke
        ex.printStackTrace();

        // Option 1: expose the real message while debugging
        return buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error",
                ex.getMessage(),               // temporary, for debugging
                req.getRequestURI()
        );

        // Option 2 (prod): keep generic message, but still log above
        // return buildError(
        //         HttpStatus.INTERNAL_SERVER_ERROR,
        //         "Internal server error",
        //         "Something went wrong",
        //         req.getRequestURI()
        // );
    }
}
