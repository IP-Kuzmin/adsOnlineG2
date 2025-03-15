package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comments {
    @Schema(description = "Количество комментариев")
    private Integer count;

    @Schema(description = "Список комментариев")
    private List<Comment> results;
}
