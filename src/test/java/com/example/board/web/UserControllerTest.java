package com.example.board.web;

import com.example.board.db.entity.User;
import com.example.board.db.repository.UserRepository;
import com.example.board.dto.UserRequestDto;
import com.example.board.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    UserService userService;
    @MockBean
    UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addUser() throws Exception {
        UserRequestDto userRequest = UserRequestDto.builder()
                .name("user1")
                .email("email1")
                .build();
        User user = User.builder()
                .name("user1")
                .email("email1")
                .build();

        when(userService.save(any(UserRequestDto.class))).thenReturn(user);

        mockMvc.perform(post("/new-user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("user1"))
                .andDo(print());

    }

    @Test
    void findUser() throws Exception {
        User user1 = User.builder()
                .id(1L)
                .name("name1")
                .email("email1")
                .build();
        User user2 = User.builder()
                .id(2L)
                .name("name2")
                .email("email2")
                .build();
        userRepository.save(user1);
        userRepository.save(user2);

        when(userService.findByEmail("email1")).thenReturn(Optional.of(user1));

        mockMvc.perform(get("/user/email1"))
                .andExpect(status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name1"))
                .andDo(print());

    }

    @Test
    void deleteUser() throws Exception {
        User user1 = User.builder()
                .id(1L)
                .name("name1")
                .email("email1")
                .build();
        User user2 = User.builder()
                .id(2L)
                .name("name2")
                .email("email2")
                .build();
        userRepository.save(user1);
        userRepository.save(user2);

        mockMvc.perform(delete("/user/email1"))
                .andExpect(status().isAccepted())
                .andDo(print());
        Assertions.assertThat(userRepository.findByEmail("email2")).isEmpty();

    }

    @Test
    void updateUser() {
    }
}