package ru.skypro.homework.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    @Schema(description = "ID пользователя")
    private Long id;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true, length = 255)
    @Schema(description = "Email пользователя")
    private String email;

    @Size(min = 2, max = 16)
    @Column(nullable = false, length = 16)
    @Schema(description = "Имя пользователя")
    private String firstName;

    @Size(min = 2, max = 16)
    @Column(nullable = false, length = 16)
    @Schema(description = "Фамилия пользователя")
    private String lastName;

    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    @Column(nullable = false, length = 20)
    @Schema(description = "Телефон пользователя")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    @Schema(description = "Роль пользователя")
    private Role role;

    @Column(length = 512)
    @Schema(description = "Ссылка на аватар пользователя")
    private String image;

    @Column(nullable = false, length = 64)
    @Schema(description = "Хешированный пароль (MD5)")
    private String password;
}
