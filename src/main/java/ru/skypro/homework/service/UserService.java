package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ChangeAndNewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;

public interface UserService {

    static String USER_NOT_FOUND = "Пользователь не найден";

    void setPassword(ChangeAndNewPassword newPassword);

    User getCurrentUser();

    User updateUser(UpdateUser updateUser);

    void updateUserImage(MultipartFile image);
}
