package SPAC.MealFlow.user.controller;

import SPAC.MealFlow.common.dto.ErrorResponseDTO;
import SPAC.MealFlow.user.dto.*;
import SPAC.MealFlow.user.model.User;
import SPAC.MealFlow.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Controller only maps DTO -> entity and entity -> DTO
    // Password hashing should be done in the service layer

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserCreateRequestDTO request) {

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .role(User.Role.USER)
                .createdAt(new Date())
                .build();

        User created = userService.createUser(user);

        UserResponseDTO response = new UserResponseDTO(
                created.getId(),
                created.getName(),
                created.getEmail(),
                created.getRole().name(),
                created.getCreatedAt()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {

        Optional<User> userOpt = userService.authenticate(request.email(), request.password());

        if (userOpt.isEmpty()) {

            ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                    HttpStatus.UNAUTHORIZED.value(),
                    "Invalid email or password"
            );

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(errorResponse);
        }

        User user = userOpt.get();

        LoginResponseDTO response = new LoginResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );

        return ResponseEntity.ok(response);
    }
}
