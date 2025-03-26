package ru.skypro.homework.service;

import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;

public interface CommentService {
    Comments getCommentsByAdId(Long adId);

    Comment addComment(Long adId, CreateOrUpdateComment comment);

    Comment updateComment(Long adId, Long commentId, CreateOrUpdateComment comment);

    void deleteComment(Long adId, Long commentId);
}
