package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Register {
    @Schema(description = "Имя пользователя", example = "Ivan")
    @NotBlank
    @Size(min = 2, max = 16)
    private String firstName;

    @Schema(description = "Фамилия пользователя", example = "Ivanov")
    @NotBlank
    @Size(min = 2, max = 16)
    private String lastName;

    @Schema(description = "Телефон пользователя", example = "+7 (999) 123-45-67")
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    private String phone;

    @Schema(description = "Email пользователя", example = "user@example.com")
    @Email
    @NotBlank
    private String username;

    @Schema(description = "Пароль", example = "password123")
    @Size(min = 8, max = 16)
    private String password;

    @Schema(description = "Роль пользователя")
    private Role role;
}
