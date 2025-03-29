package ru.skypro.homework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.exceptions.enums.ExceptionsMessage;

public class CurrentUserNotFoundResponseException extends ResponseStatusException {
    public CurrentUserNotFoundResponseException() {
        super(HttpStatus.NOT_FOUND, ExceptionsMessage.USER_NOT_FOUND.getMessage());
    }
}
