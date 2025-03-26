package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Schema(description = "ID автора комментария")
    private Integer author;

    @Schema(description = "Ссылка на аватар автора комментария")
    private String authorImage;

    @Schema(description = "Имя автора комментария")
    private String authorFirstName;

    @Schema(description = "Дата создания комментария")
    private Long createdAt;

    @Schema(description = "ID комментария")
    private Integer pk;

    @Schema(description = "Текст комментария")
    private String text;
}
