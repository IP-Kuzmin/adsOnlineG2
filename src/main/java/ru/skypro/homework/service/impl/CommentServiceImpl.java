package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
    @Override
    public Comments getCommentsByAdId(Integer adId) {
        return null;
    }

    @Override
    public Comment addComment(Integer adId, CreateOrUpdateComment comment) {
        return null;
    }

    @Override
    public Comment updateComment(Integer adId, Integer commentId, CreateOrUpdateComment comment) {
        return null;
    }

    @Override
    public void deleteComment(Integer adId, Integer commentId) {

    }
}
