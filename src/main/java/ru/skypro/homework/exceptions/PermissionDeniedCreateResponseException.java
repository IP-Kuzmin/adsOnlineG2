package ru.skypro.homework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PermissionDeniedCreateResponseException extends ResponseStatusException {
    public PermissionDeniedCreateResponseException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
