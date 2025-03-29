package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.exceptions.EmptyImageException;
import ru.skypro.homework.exceptions.ImageAdsSaveException;
import ru.skypro.homework.exceptions.ImageAvatarSaveException;
import ru.skypro.homework.exceptions.IncompatibleMediaImageException;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.enums.ImageSaveType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        if (image == null || image.isEmpty() || image.getOriginalFilename() == null) {
            throw new EmptyImageException("Empty picture error in ID: " + id + " type: " +type.name());
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
    public boolean imageValidator(String imagePath) {
        if (imagePath.startsWith("/")) {
            imagePath = imagePath.substring(1);
        }

        Path fullPath = Path.of(imagePath.replaceAll("[:*?\"<>|]", "")
                .replaceAll("/{2,}", "/"));

        if (!Files.exists(fullPath)) {
            log.error("Недоступно: {}", fullPath);
            return false;
        }
        return true;
    }

    @Override
    public String getDefaultPhoto() {
        return imageDir + imageDefault;
    }
}
