package ru.skypro.homework.mapper;

import org.mapstruct.*;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.models.UserModel;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toDto(UserModel model);

    UserModel toEntity(User dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateModel(@MappingTarget UserModel model, UpdateUser dto);
}
