package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.CommentService;

import javax.validation.Valid;

@RestController
@Tag(name = "Комментарии", description = "Управление комментариями к объявлениям")
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "Получение комментариев", description = "Возвращает список комментариев для конкретного объявления по его ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарии успешно получены"),
            @ApiResponse(responseCode = "404", description = "Объявление не найдено")
    })
    @GetMapping("/{adId}/comments")
    public ResponseEntity<Comments> getComments(@PathVariable Long adId) {
        return ResponseEntity.ok(commentService.getCommentsByAdId(adId));
    }

    @Operation(summary = "Добавление комментария", description = "Позволяет добавить новый комментарий к объявлению.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий успешно добавлен"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для добавления комментария"),
            @ApiResponse(responseCode = "404", description = "Объявление не найдено")
    })
    @PostMapping("/{adId}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Long adId, @RequestBody @Valid CreateOrUpdateComment comment) {
        return ResponseEntity.ok(commentService.addComment(adId, comment));
    }

    @Operation(summary = "Обновление комментария", description = "Позволяет обновить существующий комментарий по его ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий успешно обновлён"),
            @ApiResponse(responseCode = "404", description = "Комментарий или объявление не найдено"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для обновления комментария")
    })
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long adId, @PathVariable Long commentId,
                                                 @RequestBody @Valid CreateOrUpdateComment comment) {
        return ResponseEntity.ok(commentService.updateComment(adId, commentId, comment));
    }

    @Operation(summary = "Удаление комментария", description = "Удаляет комментарий по его ID, если он существует.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Комментарий успешно удалён"),
            @ApiResponse(responseCode = "404", description = "Комментарий или объявление не найдено")
    })
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long adId, @PathVariable Long commentId) {
        commentService.deleteComment(adId, commentId);
        return ResponseEntity.noContent().build();
    }
}
