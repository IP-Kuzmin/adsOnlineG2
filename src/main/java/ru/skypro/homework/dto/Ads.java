package ru.skypro.homework.dto;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ads {
    @Schema(description = "Общее количество объявлений")
    private Integer count;

    @Schema(description = "Список объявлений")
    private List<Ad> results;
}
