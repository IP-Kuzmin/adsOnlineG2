package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateAd {

    @Schema(description = "Заголовок объявления", example = "Продается велосипед")
    @NotBlank
    @Size(min = 4, max = 32)
    private String title;

    @Schema(description = "Цена объявления", example = "10000")
    @NotNull
    @Min(0)
    @Max(10000000)
    private Integer price;

    @Schema(description = "Описание объявления", example = "Почти новый, не был в использовании")
    @NotBlank
    @Size(min = 8, max = 64)
    private String description;
}
