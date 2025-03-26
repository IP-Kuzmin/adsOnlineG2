package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ad {
    @Schema(description = "ID объявления", example = "1")
    private Integer id;

    @Schema(description = "Заголовок объявления")
    private String title;

    @Schema(description = "Описание объявления")
    private String description;
}

