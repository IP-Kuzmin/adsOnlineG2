package ru.skypro.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import ru.skypro.homework.exceptions.IncompatibleMediaImageException;
import ru.skypro.homework.service.enums.ImageSaveType;
import ru.skypro.homework.service.enums.ImageValidationErrorType;
import ru.skypro.homework.service.impl.ImageServiceImpl;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ImageServiceTest {

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

        BufferedImage img = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        boolean result = ImageIO.write(img, "jpg", baos);
        byte[] jpegBytes = baos.toByteArray();

        MockMultipartFile mockFile = new MockMultipartFile(
                "image", "avatar.png", "image/jpeg", jpegBytes);

        String path = imageService.saveImage(mockFile, 1L, ImageSaveType.AVATAR);

        assertTrue(Files.exists(Path.of(tempDir + "/images/avatar/1.jpeg")));
        assertTrue(path.contains("/images/avatar/1.jpeg"));
    }

    @Test
    void saveImage_StopFaked() throws IOException {

        MockMultipartFile mockFile = new MockMultipartFile(
                "image", "avatar.png", "image/jpeg", "faked".getBytes());

        assertThrows(IncompatibleMediaImageException.class ,() ->
                imageService.saveImage(mockFile, 1L, ImageSaveType.AVATAR));
    }

    @Test
    void saveImage_shouldThrowExceptionIfEmpty() {
        MockMultipartFile mockFile = new MockMultipartFile(
                "image", "empty.png", "image/png", new byte[0]);

        assertThrows(IncompatibleMediaImageException.class,
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
    void saveImage_shouldThrowIfContentTypeIsNull() {
        MockMultipartFile mockFile = new MockMultipartFile(
                "image", "file.png", null, "data".getBytes());

        assertThrows(IncompatibleMediaImageException.class,
                () -> imageService.saveImage(mockFile, 6L, ImageSaveType.AD));
    }

    @Test
    void imagePathValidator_shouldReturnErrorIfFileNotExist() {
        Optional<ImageValidationErrorType> result = imageService.imagePathValidator("/non/existing/path/image.jpg");
        assertTrue(result.isPresent());
        assertEquals(ImageValidationErrorType.FILE_NOT_FOUND, result.get());
    }

    @Test
    void imagePathValidator_shouldReturnTrueIfFileExistsAndMediaFailed() throws IOException {
        Path filePath = tempDir.resolve("test.jpg");
        Files.createFile(filePath);

        String path = "/" + filePath.toString();

        assertEquals(ImageValidationErrorType.INVALID_IMAGE_FORMAT,imageService.imagePathValidator(path).orElseThrow());
    }

    @Test
    void imageMediaValidator_shouldReturnErrorIfNullFile() {
        Optional<ImageValidationErrorType> result = imageService.imageMediaValidator(null);
        assertTrue(result.isPresent());
        assertEquals(ImageValidationErrorType.FILE_IS_NULL, result.get());
    }

    @Test
    void imageMediaValidator_shouldReturnErrorIfEmptyFile() {
        MockMultipartFile mockFile = new MockMultipartFile(
                "image", "empty.png", "image/png", new byte[0]);

        Optional<ImageValidationErrorType> result = imageService.imageMediaValidator(mockFile);
        assertTrue(result.isPresent());
        assertEquals(ImageValidationErrorType.FILE_IS_EMPTY, result.get());
    }

    @Test
    void imageMediaValidator_shouldReturnErrorIfInvalidImage() {
        MockMultipartFile mockFile = new MockMultipartFile(
                "image", "not-image.png", "image/png", "not really image".getBytes());

        Optional<ImageValidationErrorType> result = imageService.imageMediaValidator(mockFile);
        assertTrue(result.isPresent());
        assertEquals(ImageValidationErrorType.INVALID_IMAGE_FORMAT, result.get());
    }

    @Test
    void imageMediaValidator_shouldReturnEmptyIfValidImage() throws IOException {

        BufferedImage img = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        boolean result = ImageIO.write(img, "jpg", baos);
        byte[] jpegBytes = baos.toByteArray();

        MockMultipartFile mockFile = new MockMultipartFile(
                "image", "valid.png", "image/png", jpegBytes);

        Optional<ImageValidationErrorType> testResult = imageService.imageMediaValidator(mockFile);
        assertTrue(testResult.isEmpty());
    }

    @Test
    void getDefaultPhoto_shouldReturnCorrectPath() {
        String defaultPhoto = imageService.getDefaultPhoto();
        assertEquals(tempDir.toString() + "/default.jpg", defaultPhoto);
    }
}
