package ru.skypro.homework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PermissionDeniedDeleteResponseException extends ResponseStatusException {
    public PermissionDeniedDeleteResponseException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
