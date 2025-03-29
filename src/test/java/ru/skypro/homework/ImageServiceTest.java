package ru.skypro.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import ru.skypro.homework.exceptions.EmptyImageException;
import ru.skypro.homework.exceptions.IncompatibleMediaImageException;
import ru.skypro.homework.service.enums.ImageSaveType;
import ru.skypro.homework.service.impl.ImageServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ImageServiceImplTest {

    @InjectMocks
    private ImageServiceImpl imageService;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        imageService = new ImageServiceImpl();

        ReflectionTestUtils.setField(imageService, "imageDir", tempDir.toString());
        ReflectionTestUtils.setField(imageService, "imageDefault", "/default.jpg");
        ReflectionTestUtils.setField(imageService, "isUseAbsolutePath", true);

    }

    @Test
    void saveImage_shouldSaveAvatarSuccessfully() throws IOException {
        MockMultipartFile mockFile = new MockMultipartFile(
                "image", "avatar.png", "image/png", "test image content".getBytes());

        String path = imageService.saveImage(mockFile, 1L, ImageSaveType.AVATAR);

        assertTrue(Files.exists(Path.of(tempDir + "/images/avatar/1.png")));
        assertTrue(path.contains("/images/avatar/1.png"));
    }

    @Test
    void saveImage_shouldThrowExceptionIfEmpty() {
        MockMultipartFile mockFile = new MockMultipartFile(
                "image", "", "image/png", new byte[0]);

        assertThrows(EmptyImageException.class,
                () -> imageService.saveImage(mockFile, 2L, ImageSaveType.AVATAR));
    }

    @Test
    void saveImage_shouldThrowIfContentTypeInvalid() {
        MockMultipartFile mockFile = new MockMultipartFile(
                "image", "file.txt", "text/plain", "not an image".getBytes());

        assertThrows(IncompatibleMediaImageException.class,
                () -> imageService.saveImage(mockFile, 3L, ImageSaveType.AD));
    }

    @Test
    void imageValidator_shouldReturnFalseIfFileNotExist() {
        boolean result = imageService.imageValidator("/non/existing/path/image.jpg");
        assertFalse(result);
    }

    @Test
    void getDefaultPhoto_shouldReturnCorrectPath() {
        String defaultPhoto = imageService.getDefaultPhoto();
        assertEquals(tempDir.toString() + "/default.jpg", defaultPhoto);
    }
}
