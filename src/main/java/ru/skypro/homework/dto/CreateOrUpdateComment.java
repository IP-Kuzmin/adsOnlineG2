package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateComment {

    @Schema(description = "Текст комментария", example = "Очень интересное объявление!")
    @NotBlank
    @Size(min = 8, max = 64)
    private String text;
}
