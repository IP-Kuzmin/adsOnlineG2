package ru.skypro.homework.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.dto.Login;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public void registerUser(Register register) {
        if (register.getUsername() == null || register.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Имя пользователя и пароль обязательны");
        }
        System.out.println("Пользователь зарегистрирован: " + register.getUsername());
    }

    @Override
    public void loginUser(Login login) {
        if ("user@example.com".equals(login.getUsername()) && "password".equals(login.getPassword())) {
            System.out.println("Пользователь успешно авторизован: " + login.getUsername());
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Неверные данные для входа");
        }
    }
}
