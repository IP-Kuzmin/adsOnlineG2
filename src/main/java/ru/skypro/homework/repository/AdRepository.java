package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.models.AdModel;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<AdModel, Long>{
    List<AdModel> findByAuthorId(Long id);
}
