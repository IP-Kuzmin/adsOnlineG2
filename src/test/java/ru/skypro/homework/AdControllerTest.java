package ru.skypro.homework;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.service.impl.AdServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class AdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AdServiceImpl adService;

    @Test
    @WithMockUser(username = "user@example.com", roles = {"USER"})
    void shouldGetAllAds() throws Exception {
        mockMvc.perform(get("/ads"))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = {"/data-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/delete-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "user@example.com", roles = {"USER"})
    void shouldCreateAdWithMultipart() throws Exception {
        MockMultipartFile properties = new MockMultipartFile(
                "properties",
                "",
                "application/json",
                objectMapper.writeValueAsBytes(new CreateOrUpdateAd("Bicycle", 1000, "Almost new bike"))
        );

        MockMultipartFile image = new MockMultipartFile(
                "image",
                "bike.jpg",
                "image/jpeg",
                "fake-image-content".getBytes()
        );

        mockMvc.perform(multipart("/ads")
                        .file(properties)
                        .file(image)
                        .with(request -> {
                            request.setMethod("POST");
                            return request;
                        }))
                .andExpect(status().isCreated());
    }

    @Test
    @Sql(scripts = {"/data-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/delete-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "user@example.com", roles = {"USER"})
    void shouldUpdateAd() throws Exception {
        CreateOrUpdateAd ad = new CreateOrUpdateAd("Updated Bike", 1200, "Updated description");

        mockMvc.perform(patch("/ads/11")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ad)))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = {"/data-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/delete-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "user@example.com", roles = {"USER"})
    void shouldDeleteAd() throws Exception {
        mockMvc.perform(delete("/ads/11"))
                .andExpect(status().isNoContent());
    }
}
