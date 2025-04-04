package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.models.CommentModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentModel, Long> {

    List<CommentModel> findByAdId(Long adId);

    Optional<CommentModel> findByIdAndAdId(Long commentId, Long adId);

    boolean existsByIdAndAdId(Long commentId, Long adId);
}
