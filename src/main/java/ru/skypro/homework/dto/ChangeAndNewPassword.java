package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeAndNewPassword {

    @Schema(description = "Текущий пароль", example = "oldPassword123")
    @NotBlank
    @Size(min = 8, max = 16)
    private String currentPassword;

    @Schema(description = "Новый пароль", example = "newPassword123")
    @NotBlank
    @Size(min = 8, max = 16)
    private String newPassword;
}
