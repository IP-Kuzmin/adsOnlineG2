package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Schema(description = "ID пользователя", example = "1")
    private Integer id;

    @Schema(description = "Email пользователя", example = "user@example.com")
    private String email;

    @Schema(description = "Имя пользователя", example = "Ivan")
    private String firstName;

    @Schema(description = "Фамилия пользователя", example = "Ivanov")
    private String lastName;

    @Schema(description = "Телефон пользователя", example = "+7 (999) 123-45-67")
    private String phone;

    @Schema(description = "Роль пользователя")
    private Role role;

    @Schema(description = "Ссылка на аватар", example = "http://example.com/image.jpg")
    private String image;
}
