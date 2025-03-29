package ru.skypro.homework.exceptions;

public class EmptyImageException extends IllegalArgumentException {
    public EmptyImageException(String s) {
        super(s);
    }
}
