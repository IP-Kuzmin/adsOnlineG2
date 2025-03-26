package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.service.CommentService;

import java.util.Collections;

@Service
public class CommentServiceImpl implements CommentService {

    @Override
    public Comments getCommentsByAdId(Integer adId) {
        return new Comments(1, Collections.singletonList(
                new Comment("Demo")
        ));
    }

    @Override
    public Comment addComment(Integer adId, CreateOrUpdateComment commentDto) {
        return new Comment("БаБаБла");
    }

    @Override
    public Comment updateComment(Integer adId, Integer commentId, CreateOrUpdateComment commentDto) {
        return new Comment("Бла Бла Бла");
    }

    @Override
    public void deleteComment(Integer adId, Integer commentId) {
    }
}

