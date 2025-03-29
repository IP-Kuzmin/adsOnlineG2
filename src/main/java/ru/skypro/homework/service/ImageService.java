package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.service.enums.ImageSaveType;
import ru.skypro.homework.service.enums.ImageValidationErrorType;

import java.util.Optional;

public interface ImageService {

    String saveImage(MultipartFile image, Long id, ImageSaveType type);

    Optional<ImageValidationErrorType> imagePathValidator(String imagePath);

    Optional<ImageValidationErrorType> imageMediaValidator(MultipartFile image);

    String getDefaultPhoto();
}
