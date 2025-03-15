package ru.skypro.homework.service;

import ru.skypro.homework.dto.MeImageBody;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;

public interface UserService {
    void setPassword(NewPassword newPassword);

    User getCurrentUser();

    User updateUser(UpdateUser updateUser);

    void updateUserImage(MeImageBody imageBody);
}
