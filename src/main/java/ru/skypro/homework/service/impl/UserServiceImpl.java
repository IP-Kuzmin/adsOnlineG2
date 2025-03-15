package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.MeImageBody;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public void setPassword(NewPassword newPassword) {

    }

    @Override
    public User getCurrentUser() {
        return null;
    }

    @Override
    public User updateUser(UpdateUser updateUser) {
        return null;
    }

    @Override
    public void updateUserImage(MeImageBody imageBody) {

    }
}
