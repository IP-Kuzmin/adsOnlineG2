package ru.skypro.homework.exceptions;

public class ImageAvatarSaveException extends RuntimeException {
    public ImageAvatarSaveException(String message) {
        super("Avatar image save Error: " + message );
    }
}
