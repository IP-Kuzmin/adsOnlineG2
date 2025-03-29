package ru.skypro.homework;

import com.fasterxml.jackson.databind.ObjectMapper;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.homework.dto.UpdateUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SpringLiquibase liquibase;

    @BeforeEach
    void setUpDatabase() throws LiquibaseException {
        jdbcTemplate.execute("DROP ALL OBJECTS");
        liquibase.afterPropertiesSet();
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"USER"})
    void shouldGetUserInfo() throws Exception {
        mockMvc.perform(get("/users/me"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"USER"})
    void shouldUpdateUserInfo() throws Exception {
        UpdateUser updateUser = new UpdateUser("John", "Doe", "+71234567890");

        mockMvc.perform(patch("/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUser)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"USER"})
    void shouldUpdateUserInfo_BadRequest() throws Exception {
        UpdateUser updateUser = new UpdateUser("John", "Doe", "+11234567890");

        mockMvc.perform(patch("/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUser)))
                .andExpect(status().isBadRequest());
    }
}
