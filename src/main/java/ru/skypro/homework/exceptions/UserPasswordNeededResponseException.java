package ru.skypro.homework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserPasswordNeededResponseException extends ResponseStatusException {
    public UserPasswordNeededResponseException() {
        super(HttpStatus.BAD_REQUEST, ExceptionsMessage.PASSWORD_NEEDED.getMessage());
    }
}
