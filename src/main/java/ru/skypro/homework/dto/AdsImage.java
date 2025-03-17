package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdsImage {
    @Schema(description = "ID изображения", example = "123")
    private Integer id;
}
