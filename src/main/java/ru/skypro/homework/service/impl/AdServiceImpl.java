package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdService;

import java.util.Collections;

@Service
public class AdServiceImpl implements AdService {

    @Override
    public Ads getAllAds() {
        return new Ads(0, Collections.emptyList());
    }

    @Override
    public Ad createAd(CreateOrUpdateAd adDto) {
        return new Ad(1, "Demo", "image-placeholder.jpg");
    }

    @Override
    public ExtendedAd getAdById(Integer id) {
        return new ExtendedAd(
                id,
                "John",
                "Doe",
                "Описание объявления для теста",
                "john.doe@example.com",
                "https://example.com/image.jpg",
                "+7 (123) 456-78-90",
                999,
                "Тестовое объявление"
        );
    }


    @Override
    public Ad updateAd(Integer id, CreateOrUpdateAd adDto) {
        return new Ad(id, "Demo2", "updated-image.jpg");
    }

    @Override
    public void deleteAd(Integer id) {
        // Пустой метод для тестов
    }

    @Override
    public Ads getUserAds() {
        return new Ads(0, Collections.emptyList());
    }

    @Override
    public void updateAdImage(Integer id, IdImageBody imageBody) {
        // Пустой метод для тестов
    }
}
