package SPAC.MealFlow.user.controller;

import SPAC.MealFlow.user.dto.LoginRequestDTO;
import SPAC.MealFlow.user.dto.UserCreateRequestDTO;
import SPAC.MealFlow.user.model.User;
import SPAC.MealFlow.user.service.UserService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;


import java.util.Optional;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;


    // --------------------
    // CREATE USER TEST
    // --------------------
    @Test
    void testCreateUser() throws Exception {

        UserCreateRequestDTO reqDto = new UserCreateRequestDTO(
                "testuser",
                "test@mail.dk",
                "password"
        );

        User created = User.builder()
                .id(1)
                .name("testuser")
                .email("test@mail.dk")
                .password("hashed")
                .role(User.Role.USER)
                .createdAt(new Date())
                .build();

        when(userService.createUser(any(User.class))).thenReturn(created);

        mockMvc.perform(post("/api/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reqDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@mail.dk"))
                .andExpect(jsonPath("$.role").value("USER"));
    }


    // --------------------
    // LOGIN TEST
    // --------------------
    @Test
    void testLoginUser() throws Exception {

        LoginRequestDTO loginDto = new LoginRequestDTO(
                "test@mail.dk",
                "password"
        );

        User user = User.builder()
                .id(1)
                .name("testuser")
                .email("test@mail.dk")
                .password("hashed")
                .role(User.Role.USER)
                .createdAt(new Date())
                .build();

        when(userService.authenticate(any(String.class), any(String.class)))
                .thenReturn(Optional.of(user));

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@mail.dk"))
                .andExpect(jsonPath("$.name").value("testuser"));
    }
}
