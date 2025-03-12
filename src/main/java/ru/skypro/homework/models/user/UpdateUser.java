package ru.skypro.homework.models.user;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UpdateUser {

    @Schema(description = "имя пользователя")
    @Size(min = 3, max = 10, message = "Имя пользователя должно содержать от 3 до 10 символов")
    private String firstName;

    @Schema(description = "фамилия пользователя")
    @Size(min = 3, max = 10, message = "Фамилия пользователя должна содержать от 3 до 10 символов")
    private String lastName;

    @Schema(description = "телефон пользователя")
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}",
            message = "Неправильный формат телефона. Ожидается: +7 xxx xxx-xx-xx")
    private String phone;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }



}
