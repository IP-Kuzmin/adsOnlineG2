package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.exceptions.AdNotFoundResponseException;
import ru.skypro.homework.exceptions.CommentNotFoundResponseException;
import ru.skypro.homework.exceptions.CurrentUserNotFoundResponseException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.models.AdModel;
import ru.skypro.homework.models.CommentModel;
import ru.skypro.homework.models.UserModel;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;

import java.time.Instant;
import java.util.List;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Override
    public Comments getCommentsByAdId(Long adId) {
        List<CommentModel> models = commentRepository.findByAdId(adId);
        List<Comment> comments = models.stream().map(commentMapper::toDto).toList();
        log.debug("Загружено {} комментариев к объявлению id={}", comments.size(), adId);
        return new Comments(comments.size(), comments);
    }

    @Override
    public Comment addComment(Long adId, CreateOrUpdateComment dto) {
        AdModel ad = adRepository.findById(adId)
                .orElseThrow(AdNotFoundResponseException::new);

        UserModel author = getCurrentUser();

        CommentModel comment = new CommentModel();
        comment.setText(dto.getText());
        comment.setCreatedAt(Instant.now().toEpochMilli());
        comment.setAuthor(author);
        comment.setAd(ad);

        CommentModel saved = commentRepository.save(comment);
        log.info("Комментарий id={} добавлен к объявлению id={}", saved.getId(), adId);
        return commentMapper.toDto(saved);
    }

    @Override
    public Comment updateComment(Long adId, Long commentId, CreateOrUpdateComment dto) {
        CommentModel comment = commentRepository.findByIdAndAdId(commentId, adId)
                .orElseThrow(CommentNotFoundResponseException::new);

        comment.setText(dto.getText());
        CommentModel updated = commentRepository.save(comment);
        log.info("Комментарий id={} обновлён", updated.getId());
        return commentMapper.toDto(updated);
    }

    @Override
    public void deleteComment(Long adId, Long commentId) {
        if (!commentRepository.existsByIdAndAdId(commentId, adId)) {
            throw new CommentNotFoundResponseException();
        }
        commentRepository.deleteById(commentId);
        log.info("Комментарий id={} удалён из объявления id={}", commentId, adId);
    }

    private UserModel getCurrentUser() {
        String email = getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(CurrentUserNotFoundResponseException::new);
    }
}
