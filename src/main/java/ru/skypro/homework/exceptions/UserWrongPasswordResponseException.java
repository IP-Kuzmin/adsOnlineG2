package ru.skypro.homework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.exceptions.enums.ExceptionsMessage;

public class UserWrongPasswordResponseException extends ResponseStatusException {
    public UserWrongPasswordResponseException() {
        super(HttpStatus.FORBIDDEN, ExceptionsMessage.WRONG_PASSWORD.getMessage());
    }
}
