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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.service.impl.AdServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AdServiceImpl adService;

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
    @WithMockUser(username = "oleg@example.com", roles = {"ADMIN"})
    void shouldGetComments() throws Exception {
        mockMvc.perform(get("/ads/3/comments"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "oleg@example.com", roles = {"ADMIN"})
    void shouldAddComment() throws Exception {
        CreateOrUpdateComment comment = new CreateOrUpdateComment("Great product!");

        mockMvc.perform(post("/ads/3/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "oleg@example.com", roles = {"ADMIN"})
    void shouldUpdateComment() throws Exception {
        CreateOrUpdateComment comment = new CreateOrUpdateComment("Updated comment text");

        mockMvc.perform(patch("/ads/3/comments/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "oleg@example.com", roles = {"ADMIN"})
    void shouldDeleteComment_PermissionAdmin() throws Exception {
        mockMvc.perform(delete("/ads/3/comments/3"))
                .andExpect(status().isNoContent());
    }

}
