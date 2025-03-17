package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AuthService;

import javax.validation.Valid;

@RestController
@CrossOrigin(value = "http://localhost:3000")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            summary = "Регистрация пользователя",
            description = "Эндпоинт для создания нового пользователя с именем, паролем, email и ролью.",
            tags = {"Регистрация"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь успешно зарегистрирован"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для регистрации"),
            @ApiResponse(responseCode = "409", description = "Пользователь с таким email уже существует")
    })
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid Register register) {
        authService.registerUser(register);
        return ResponseEntity.status(201).build();
    }

    @Operation(
            summary = "Авторизация пользователя",
            description = "Эндпоинт для авторизации пользователя с использованием email и пароля.",
            tags = {"Авторизация"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная авторизация"),
            @ApiResponse(responseCode = "401", description = "Неверные учетные данные"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid Login login) {
        authService.loginUser(login);
        return ResponseEntity.ok().build();
    }
}
