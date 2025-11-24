package SPAC.MealFlow.user.controller;

import SPAC.MealFlow.common.dto.ErrorResponseDTO;
import SPAC.MealFlow.common.exceptions.UserAlreadyExistsException;
import SPAC.MealFlow.security.CustomUserDetails;
import SPAC.MealFlow.security.JwtService;
import SPAC.MealFlow.user.dto.LoginRequestDTO;
import SPAC.MealFlow.user.dto.LoginResponseDTO;
import SPAC.MealFlow.user.dto.UserCreateRequestDTO;
import SPAC.MealFlow.user.dto.UserResponseDTO;
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

    private final JwtService jwtService;

    private final UserService userService;

    @Autowired
    public UserController(JwtService jwtService,
                          UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserCreateRequestDTO request) {

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .role(User.Role.USER)
                .createdAt(new Date())
                .build();

        try {
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

        } catch (UserAlreadyExistsException ex) {

            ErrorResponseDTO error = new ErrorResponseDTO(
                    HttpStatus.CONFLICT.value(),
                    ex.getMessage()
            );

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(error);
        }
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

        CustomUserDetails principal = new CustomUserDetails(user);
        String token = jwtService.generateToken(principal);

        LoginResponseDTO response = new LoginResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                token
        );

        return ResponseEntity.ok(response);
    }
}
