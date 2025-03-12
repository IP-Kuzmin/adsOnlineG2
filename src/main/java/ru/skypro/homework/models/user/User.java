package ru.skypro.homework.models.user;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

public class User {

    //id пользователя
    @Schema(description = "id пользователя")
    private Integer id;

    //логин пользователя
    @Schema(description = "логин пользователя")
    private String email;

    //имя пользователя
    @Schema(description = "имя пользователя")
    private String firstName;

    //фамилия пользователя
    @Schema(description = "фамилия пользователя")
    private String lastName;

    //телефон пользователя
    @Schema(description = "телефон пользователя")
    private String phone;

    //роль пользователя
    @Schema(description = "роль пользователя")
    private String role;

    //ссылка на аватар пользователя
    @Schema(description = "ссылка на аватар пользователя")
    private String image;

    //Конструктор для создания пользователя
    public User(Integer id, String email, String firstName, String lastName, String phone, String role, String image) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.role = role;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }

    public String getImage() {
        return image;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(phone, user.phone) && Objects.equals(role, user.role) && Objects.equals(image, user.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstName, lastName, phone, role, image);
    }
}