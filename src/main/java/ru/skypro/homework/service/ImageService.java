package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.service.enums.ImageSaveType;

public interface ImageService {

    String saveImage(MultipartFile image, Long id, ImageSaveType type);

    boolean imageValidator(String imagePath);

    String getDefaultPhoto();
}
