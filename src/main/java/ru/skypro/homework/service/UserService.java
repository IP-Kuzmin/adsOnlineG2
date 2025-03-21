package ru.skypro.homework.service;

import ru.skypro.homework.dto.AvatarImage;
import ru.skypro.homework.dto.ChangeAndNewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;

public interface UserService {
    void setPassword(ChangeAndNewPassword newPassword);

    User getCurrentUser();

    User updateUser(UpdateUser updateUser);

    void updateUserImage(AvatarImage imageBody);
}
