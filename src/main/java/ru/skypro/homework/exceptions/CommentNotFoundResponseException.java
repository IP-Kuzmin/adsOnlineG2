package ru.skypro.homework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CommentNotFoundResponseException extends ResponseStatusException {
    public CommentNotFoundResponseException() {
        super(HttpStatus.NOT_FOUND, ExceptionsMessage.COMMENT_NOT_FOUND.getMessage());
    }
}
