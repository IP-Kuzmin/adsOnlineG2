package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ad {

    @Schema(description = "ID объявления")
    private Integer pk;

    @Schema(description = "ID автора")
    private Integer author;

    @Schema(description = "Ссылка на изображение объявления")
    private String image;

    @Schema(description = "Цена объявления")
    private Integer price;

    @Schema(description = "Заголовок объявления")
    private String title;
}
