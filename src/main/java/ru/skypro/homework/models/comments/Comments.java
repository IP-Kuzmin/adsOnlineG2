package ru.skypro.homework.models.comments;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Objects;

public class Comments {

    @Schema(description = "Общее количество комментариев", example = "10")
    private Integer count;

    @Schema(description = "Список комментариев")
    private List<Comment> results;

    public Comments(Integer count, List<Comment> results) {
        this.count = count;
        this.results = results;
    }

    public Integer getCount() {
        return count;
    }

    public List<Comment> getResults() {
        return results;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Comments comments = (Comments) object;
        return Objects.equals(count, comments.count) && Objects.equals(results, comments.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, results);
    }
}
