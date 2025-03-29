package ru.skypro.homework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundResponseException extends ResponseStatusException {
    public UserNotFoundResponseException() {
        super(HttpStatus.NOT_FOUND, ExceptionsMessage.USER_NOT_FOUND.getMessage());
    }
}
