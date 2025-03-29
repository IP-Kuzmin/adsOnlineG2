package ru.skypro.homework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PermissionDeniedUpdateResponseException extends ResponseStatusException {
    public PermissionDeniedUpdateResponseException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
