package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {


}
