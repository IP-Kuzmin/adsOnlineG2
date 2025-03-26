package ru.skypro.homework.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "ads")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    @Schema(description = "ID объявления")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    @Schema(description = "Автор объявления")
    private UserModel author;

    @NotBlank
    @Size(max = 32)
    @Column(nullable = false, length = 32)
    @Schema(description = "Заголовок объявления")
    private String title;

    @PositiveOrZero
    @Column(nullable = false)
    @Schema(description = "Цена объявления")
    private Integer price;

    @Size(min = 8, max = 64)
    @Column(nullable = false, length = 64)
    @Schema(description = "Описание объявления")
    private String description;

    @Column(length = 512)
    @Schema(description = "Ссылка на изображение объявления")
    private String image;
}
