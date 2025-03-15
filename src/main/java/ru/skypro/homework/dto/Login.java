package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login {

    @Schema(description = "Email пользователя для входа", example = "user@example.com")
    @Email(message = "Email должен быть корректным")
    @NotBlank(message = "Email не может быть пустым")
    private String username;

    @Schema(description = "Пароль пользователя для входа", example = "password123")
    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 8, max = 16, message = "Пароль должен содержать от 8 до 16 символов")
    private String password;
}

