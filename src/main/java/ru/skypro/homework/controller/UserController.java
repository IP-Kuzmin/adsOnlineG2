package ru.skypro.homework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.models.user.NewPassword;
import ru.skypro.homework.models.user.UpdateUser;
import ru.skypro.homework.models.user.User;
import ru.skypro.homework.repository.UserRepository;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Обновление пароля
    @PostMapping("/set_password")
    public ResponseEntity<Void> setPassword(@RequestBody NewPassword newPassword) {

        return ResponseEntity.ok().build();
    }

    //Получение информации об авторизованном пользователе
    @GetMapping("/me")
    public ResponseEntity<User> getUser() {

        User user = new User(123, "kakaha@mail.ru", "Олег", "Олегов", "+7123456787", "пользователь", "ссылка");
        return ResponseEntity.ok(user);
    }

    //Обновление информации об авторизованном пользователе
    @PatchMapping("/me")
    public ResponseEntity<UpdateUser> updateUser(@RequestBody UpdateUser updateUser) {

        User updatedUser = new User(123, "kakaha@mail.ru", "Олег", "Олегов", "+7123456787", "пользователь", "ссылка");
        return ResponseEntity.ok(updateUser);
    }

    public ResponseEntity<Void> updateUserAvatar(@RequestParam("image") MultipartFile image) {

        return ResponseEntity.ok().build();
    }
}
