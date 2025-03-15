package ru.skypro.homework.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ads")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID объявления")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    @Schema(description = "Автор объявления")
    private UserModel author;

    @NotBlank
    @Column(nullable = false)
    @Schema(description = "Заголовок объявления")
    private String title;

    @PositiveOrZero
    @Column(nullable = false)
    @Schema(description = "Цена объявления")
    private Integer price;

    @Size(min = 8, max = 64)
    @Column(length = 64)
    @Schema(description = "Описание объявления")
    private String description;

    @Schema(description = "Ссылка на изображение объявления")
    private String image;
}
