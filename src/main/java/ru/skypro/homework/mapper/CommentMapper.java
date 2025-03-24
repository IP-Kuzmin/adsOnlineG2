package ru.skypro.homework.mapper;

import org.mapstruct.*;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.models.CommentModel;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "id", target = "pk", numberFormat = "#")
    @Mapping(source = "author.id", target = "author")
    @Mapping(source = "authorImage", target = "authorImage")
    @Mapping(source = "authorFirstName", target = "authorFirstName")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "text", target = "text")
    Comment toDto(CommentModel model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "authorImage", ignore = true)
    @Mapping(target = "authorFirstName", ignore = true)
    @Mapping(target = "ad", ignore = true)
    CommentModel toEntity(Comment dto);
}
