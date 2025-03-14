package ru.skypro.homework.models.comments;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
public class CreateOrUpdateComment {

    @Schema(description = "текст комментария")
    @Size(min = 8, max = 64, message = "Комментарий должен содержать от 8 до 64 символов")
    private String text;

    public CreateOrUpdateComment(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CreateOrUpdateComment that = (CreateOrUpdateComment) object;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}
