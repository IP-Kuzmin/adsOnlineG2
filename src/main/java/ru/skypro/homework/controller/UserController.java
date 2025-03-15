package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Tag(name = "Пользователи")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/set_password")
    @Operation(
            summary = "Смена пароля",
            description = "Позволяет пользователю изменить текущий пароль на новый."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пароль успешно обновлён"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещён")
    })
    public ResponseEntity<Void> setPassword(@RequestBody @Valid NewPassword newPassword) {
        userService.setPassword(newPassword);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    @Operation(
            summary = "Получение информации о пользователе",
            description = "Возвращает информацию о текущем авторизованном пользователе."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация успешно получена"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован")
    })
    public ResponseEntity<User> getUserInfo() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @PatchMapping("/me")
    @Operation(
            summary = "Обновление информации о пользователе",
            description = "Позволяет пользователю обновить свои личные данные."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация успешно обновлена"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован")
    })
    public ResponseEntity<User> updateUserInfo(@RequestBody @Valid UpdateUser updateUser) {
        return ResponseEntity.ok(userService.updateUser(updateUser));
    }

    @PatchMapping("/me/image")
    @Operation(
            summary = "Обновление изображения пользователя",
            description = "Позволяет пользователю обновить своё изображение профиля."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Изображение успешно обновлено"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован")
    })
    public ResponseEntity<Void> updateUserImage(@RequestBody MeImageBody imageBody) {
        userService.updateUserImage(imageBody);
        return ResponseEntity.ok().build();
    }
}