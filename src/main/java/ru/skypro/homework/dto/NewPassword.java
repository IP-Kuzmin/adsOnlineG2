package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewPassword {
    @Schema(description = "Текущий пароль", example = "oldPassword123")
    private String currentPassword;

    @Schema(description = "Новый пароль", example = "newPassword123")
    private String newPassword;
}