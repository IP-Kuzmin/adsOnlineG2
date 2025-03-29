package ru.skypro.homework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.exceptions.enums.ExceptionsMessage;

public class AdNotFoundResponseException extends ResponseStatusException {
    public AdNotFoundResponseException() {
        super(HttpStatus.NOT_FOUND, ExceptionsMessage.AD_NOT_FOUND.getMessage());
    }
}
