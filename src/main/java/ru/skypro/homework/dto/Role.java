package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Роль пользователя")
public enum Role {
    @Schema(description = "Пользователь с обычными правами")
    USER,

    @Schema(description = "Администратор с расширенными правами")
    ADMIN
}