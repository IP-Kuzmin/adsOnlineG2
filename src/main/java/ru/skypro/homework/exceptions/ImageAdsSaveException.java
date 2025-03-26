package ru.skypro.homework.exceptions;

public class ImageAdsSaveException extends RuntimeException {
    public ImageAdsSaveException(String message) {

        super("Ads image save Error " + message);
    }
}
