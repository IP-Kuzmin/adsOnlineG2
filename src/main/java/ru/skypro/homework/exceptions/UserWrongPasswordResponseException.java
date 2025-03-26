package ru.skypro.homework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserWrongPasswordResponseException extends ResponseStatusException {
    public UserWrongPasswordResponseException() {
        super(HttpStatus.FORBIDDEN, ExceptionsMessage.WRONG_PASSWORD.getMessage());
    }
}
