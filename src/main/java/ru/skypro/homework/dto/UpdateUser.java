package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUser {

    @Schema(description = "Имя пользователя", example = "Ivan")
    @Size(min = 3, max = 10)
    private String firstName;

    @Schema(description = "Фамилия пользователя", example = "Ivanov")
    @Size(min = 3, max = 10)
    private String lastName;

    @Schema(description = "Телефон пользователя", example = "+7 (999) 123-45-67")
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    private String phone;
}