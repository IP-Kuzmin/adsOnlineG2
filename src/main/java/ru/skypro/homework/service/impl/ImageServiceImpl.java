package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.exceptions.ImageAdsSaveException;
import ru.skypro.homework.exceptions.ImageAvatarSaveException;
import ru.skypro.homework.exceptions.IncompatibleMediaImageException;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.enums.ImageSaveType;
import ru.skypro.homework.service.enums.ImageValidationErrorType;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {

    @Value("${app.upload.dir}")
    private String imageDir;

    @Value("${app.photo}")
    private String imageDefault;

    @Value("${app.upload.absolute-path:true}")
    private boolean isUseAbsolutePath;


    @Override
    public String saveImage(MultipartFile image, Long id, ImageSaveType type) {
        if (imageMediaValidator(image).isPresent()) {
            throw new IncompatibleMediaImageException("Empty picture error in ID: " + id + " type: " + type.name());
        }

        String extension = getExtensionFromContentType(image.getContentType());
        String filename = id + "." + extension;

        String absolutePath = "";
        if (!isUseAbsolutePath) {
            absolutePath = System.getProperty("user.dir");
        }

        String subDir = switch (type) {
            case AVATAR -> "/images/avatar/";
            case AD -> "/images/ads/";
        };

        Path savePath = Paths.get(absolutePath, imageDir + subDir + filename);

        try {
            Files.createDirectories(savePath.getParent());
            image.transferTo(savePath.toFile());
        } catch (IOException e) {
            if (type == ImageSaveType.AVATAR) {
                throw new ImageAvatarSaveException(e.getMessage());
            } else {
                throw new ImageAdsSaveException(e.getMessage());
            }
        }

        return "/" + imageDir + subDir + filename;
    }

    private String getExtensionFromContentType(String contentType) {
        if (contentType == null  || !contentType.startsWith("image/")) {
            throw new IncompatibleMediaImageException("ContentType: " + contentType);
        }
        return contentType.substring(contentType.lastIndexOf("/") + 1);
    }

    @Override
    public Optional<ImageValidationErrorType> imagePathValidator(String imagePath) {
        if (imagePath == null || imagePath.isBlank()) {
            return Optional.of(ImageValidationErrorType.PATH_IS_NULL_OR_BLANK);
        }

        if (imagePath.startsWith("/")) {
            imagePath = imagePath.substring(1);
        }

        Path fullPath = Path.of(imagePath.replaceAll("[*?\"<>|]", "")
                .replaceAll("/{2,}", "/"));

        if (!Files.exists(fullPath)) {
            return Optional.of(ImageValidationErrorType.FILE_NOT_FOUND);
        }

        try (var is = Files.newInputStream(fullPath)) {
            if (ImageIO.read(is) == null) {
                return Optional.of(ImageValidationErrorType.INVALID_IMAGE_FORMAT);
            }
        } catch (IOException e) {
            return Optional.of(ImageValidationErrorType.IO_ERROR);
        }

        return Optional.empty();
    }


    @Override
    public Optional<ImageValidationErrorType> imageMediaValidator(MultipartFile file) {
        if (file == null) {
            return Optional.of(ImageValidationErrorType.FILE_IS_NULL);
        }

        if (file.isEmpty()) {
            return Optional.of(ImageValidationErrorType.FILE_IS_EMPTY);
        }

        try (var is = file.getInputStream()) {
            if (ImageIO.read(is) == null) {
                return Optional.of(ImageValidationErrorType.INVALID_IMAGE_FORMAT);
            }
        } catch (IOException e) {
            return Optional.of(ImageValidationErrorType.IO_ERROR);
        }

        return Optional.empty();
    }

    @Override
    public String getDefaultPhoto() {
        return imageDir + imageDefault;
    }
}
