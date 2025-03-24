package ru.skypro.homework.exceptions;

import lombok.Getter;

@Getter
public enum ExceptionsMessage {

    USER_NOT_FOUND("Пользователь не найден"),
    AD_NOT_FOUND("Объявление не найдено"),
    COMMENT_NOT_FOUND("Комментарий не найден"),
    WRONG_PASSWORD("Неверный текущий пароль"),
    EMAIL_ALREADY_EXISTS("Пользователь с таким email уже существует"),
    ACCESS_DENIED("Доступ запрещён"),
    PASSWORD_NEEDED("Пароль обязателен");

    private final String message;

    ExceptionsMessage(String message) {
        this.message = message;
    }

}