package ru.skypro.homework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.models.comments.Comment;
import ru.skypro.homework.repository.CommentRepository;

import java.util.List;

@RestController
@RequestMapping("/ads")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<Comment>> getComments(@PathVariable("id") Integer adId) {

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable("id") Integer adId,
                                              @RequestBody Comment comment) {

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Comment> deleteComment(@PathVariable("adId") Integer adId,
                                                 @PathVariable("commentId") Integer commentId) {

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable("adId") Integer adId,
                                                 @PathVariable("commentId") Integer commentId,
                                                 @RequestBody Comment updatedComment) {

        return ResponseEntity.ok().build();
    }
}
