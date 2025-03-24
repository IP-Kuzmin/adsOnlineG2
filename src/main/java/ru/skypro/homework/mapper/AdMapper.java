package ru.skypro.homework.mapper;

import org.mapstruct.*;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.models.*;

@Mapper(componentModel = "spring")
public interface AdMapper {

    // AdModel в Ad
    @Mapping(source = "id", target = "pk", numberFormat = "#")
    @Mapping(source = "author.id", target = "author")
    Ad toDto(AdModel model);

    // Ad в AdModel
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "description", ignore = true)
    AdModel toEntity(Ad dto);

    // AdModel в ExtendedAd
    @Mapping(source = "id", target = "pk", numberFormat = "#")
    @Mapping(source = "author.firstName", target = "authorFirstName")
    @Mapping(source = "author.lastName", target = "authorLastName")
    @Mapping(source = "author.email", target = "email")
    @Mapping(source = "author.phone", target = "phone")
    @Mapping(source = "author.image", target = "image")
    ExtendedAd toExtendedDto(AdModel model);

    // CreateOrUpdateAd в AdModel
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "image", ignore = true)
    AdModel fromCreateDto(CreateOrUpdateAd dto);

    // Обновление AdModel по DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "image", ignore = true)
    void updateModel(@MappingTarget AdModel model, CreateOrUpdateAd dto);
}
