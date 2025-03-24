package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.models.AdModel;
import ru.skypro.homework.models.CommentModel;
import ru.skypro.homework.models.UserModel;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AdRepository adRepository;
    private final CommentMapper commentMapper;

    @Override
    public Comments getCommentsByAdId(Integer adId) {
        List<Comment> result = commentRepository.findAll().stream()
                .filter(c -> c.getAd().getId().equals(adId))
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
        return new Comments(result.size(), result);
    }

    @Override
    public Comment addComment(Integer adId, CreateOrUpdateComment dto) {
        AdModel ad = adRepository.findById(adId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Объявление не найдено"));
        UserModel author = userRepository.findById(1L)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Пользователь не найден"));

        CommentModel comment = new CommentModel();
        comment.setText(dto.getText());
        comment.setCreatedAt(System.currentTimeMillis());
        comment.setAuthor(author);
        comment.setAuthorImage(author.getImage());
        comment.setAuthorFirstName(author.getFirstName());
        comment.setAd(ad);

        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public Comment updateComment(Integer adId, Integer commentId, CreateOrUpdateComment dto) {
        CommentModel comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Комментарий не найден"));

        comment.setText(dto.getText());
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Integer adId, Integer commentId) {
        commentRepository.deleteById(commentId);
    }
}
