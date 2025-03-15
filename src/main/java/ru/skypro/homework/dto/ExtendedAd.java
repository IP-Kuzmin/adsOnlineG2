package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtendedAd {

    @Schema(description = "ID объявления", example = "1")
    private Integer pk;

    @Schema(description = "Имя автора", example = "Ivan")
    private String authorFirstName;

    @Schema(description = "Фамилия автора", example = "Ivanov")
    private String authorLastName;

    @Schema(description = "Описание объявления")
    private String description;

    @Schema(description = "Email автора", example = "ivan@example.com")
    private String email;

    @Schema(description = "Ссылка на изображение объявления", example = "http://example.com/image.jpg")
    private String image;

    @Schema(description = "Телефон автора", example = "+7 (999) 123-45-67")
    private String phone;

    @Schema(description = "Цена объявления", example = "10000")
    private Integer price;

    @Schema(description = "Заголовок объявления", example = "Продам велосипед")
    private String title;
}

