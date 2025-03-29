package ru.skypro.homework.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID комментария")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    @Schema(description = "Автор комментария")
    private UserModel author;

    @Column(name = "author_image", length = 512)
    @Schema(description = "Ссылка на аватар автора")
    private String authorImage;

    @Column(name = "author_first_name", length = 64)
    @Schema(description = "Имя автора комментария")
    private String authorFirstName;

    @Column(name = "created_at", nullable = false)
    @Schema(description = "Дата создания комментария (timestamp)")
    private Long createdAt;

    @Size(min = 8, max = 64)
    @Column(nullable = false, length = 64)
    @Schema(description = "Текст комментария")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ad_id")
    private AdModel ad;
}