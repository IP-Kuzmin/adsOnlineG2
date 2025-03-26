package ru.skypro.homework.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID пользователя")
    private Integer id;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    @Schema(description = "Email пользователя")
    private String email;

    @Size(min = 2, max = 16)
    @Column(nullable = false)
    @Schema(description = "Имя пользователя")
    private String firstName;

    @Size(min = 2, max = 16)
    @Column(nullable = false)
    @Schema(description = "Фамилия пользователя")
    private String lastName;

    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    @Column(nullable = false)
    @Schema(description = "Телефон пользователя")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Роль пользователя")
    private Role role;

    @Schema(description = "Ссылка на аватар пользователя")
    private String image;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AdModel> ads;
}

