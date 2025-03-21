package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvatarImage {
    @Schema(description = "Ссылка на изображение профиля")
    private String image;
}
