package ru.skypro.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.exceptions.AdNotFoundResponseException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.models.AdModel;
import ru.skypro.homework.models.CommentModel;
import ru.skypro.homework.models.UserModel;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.CommentServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CommentServiceImplTest {

    private CommentServiceImpl commentService;
    private CommentRepository commentRepository;
    private AdRepository adRepository;
    private UserRepository userRepository;
    private CommentMapper commentMapper;

    @BeforeEach
    void setup() {
        commentRepository = mock(CommentRepository.class);
        adRepository = mock(AdRepository.class);
        userRepository = mock(UserRepository.class);
        commentMapper = mock(CommentMapper.class);
        commentService = new CommentServiceImpl(commentRepository, adRepository, userRepository, commentMapper);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("user@example.com", null));
    }

    @Test
    void addComment_shouldReturnComment() {
        CreateOrUpdateComment dto = new CreateOrUpdateComment();
        dto.setText("test");

        AdModel ad = new AdModel();
        ad.setId(1L);

        UserModel user = new UserModel();
        user.setEmail("test@mail.com");

        when(adRepository.findById(1L)).thenReturn(Optional.of(ad));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(commentRepository.save(any(CommentModel.class))).thenReturn(new CommentModel());
        when(commentMapper.toDto(any(CommentModel.class))).thenReturn(new Comment());

        assertNotNull(commentService.addComment(1L, dto));
        verify(commentRepository).save(any());
    }

    @Test
    void addComment_shouldThrow_ifAdNotFound() {
        when(adRepository.findById(anyLong())).thenReturn(Optional.empty());
        CreateOrUpdateComment dto = new CreateOrUpdateComment("не то");
        assertThrows(AdNotFoundResponseException.class, () -> commentService.addComment(1L, dto));
    }
}
