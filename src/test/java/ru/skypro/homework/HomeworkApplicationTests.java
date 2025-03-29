package ru.skypro.homework;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.skypro.homework.controller.AdController;
import ru.skypro.homework.controller.AuthController;
import ru.skypro.homework.controller.CommentController;
import ru.skypro.homework.controller.UserController;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class HomeworkApplicationTests {

    @Autowired private AdController adController;
    @Autowired private AuthController authController;
    @Autowired private CommentController commentController;
    @Autowired private UserController userController;

    @Autowired private AdService adService;
    @Autowired private AuthService authService;
    @Autowired private CommentService commentService;
    @Autowired private UserService userService;
    @Autowired private ImageService imageService;

    @Autowired private AdMapper adMapper;
    @Autowired private UserMapper userMapper;
    @Autowired private CommentMapper commentMapper;

    @Autowired private AdRepository adRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CommentRepository commentRepository;

    @Test
    void contextLoads() {
        // Контроллеры
        assertNotNull(adController);
        assertNotNull(authController);
        assertNotNull(commentController);
        assertNotNull(userController);

        // Сервисы
        assertNotNull(adService);
        assertNotNull(authService);
        assertNotNull(commentService);
        assertNotNull(userService);
        assertNotNull(imageService);

        // Мапперы
        assertNotNull(adMapper);
        assertNotNull(userMapper);
        assertNotNull(commentMapper);

        // Репозитории
        assertNotNull(adRepository);
        assertNotNull(userRepository);
        assertNotNull(commentRepository);
    }
}
