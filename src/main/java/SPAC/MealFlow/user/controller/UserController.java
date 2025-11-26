package SPAC.MealFlow.user.controller;

import SPAC.MealFlow.user.dto.*;
import SPAC.MealFlow.user.model.User;
import SPAC.MealFlow.user.service.UserService;
import SPAC.MealFlow.auth.user.AuthUserDetails;
import SPAC.MealFlow.auth.jwt.JwtTokenService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final JwtTokenService jwtService;
    private final UserService userService;

    public UserController(JwtTokenService jwtService, UserService userService) {
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

        User created = userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new UserResponseDTO(
                        created.getId(),
                        created.getName(),
                        created.getEmail(),
                        created.getRole().name(),
                        created.getCreatedAt()
                ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {

        User user = userService.authenticateOrThrow(request.email(), request.password());
        String token = jwtService.generateToken(new AuthUserDetails(user));

        return ResponseEntity.ok(
                new LoginResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        token
                )
        );
    }
}
