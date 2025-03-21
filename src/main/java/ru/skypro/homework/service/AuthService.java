package ru.skypro.homework.service;

import ru.skypro.homework.dto.Login;
import ru.skypro.homework.dto.Register;

public interface AuthService {

    void registerUser(Register register);

    void loginUser(Login login);
}
