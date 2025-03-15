package ru.skypro.homework.dto;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdsBody {
    @Schema(description = "ID объявления")
    private Integer id;
}