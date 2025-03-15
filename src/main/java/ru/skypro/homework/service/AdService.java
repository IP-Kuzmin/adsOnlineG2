package ru.skypro.homework.service;

import ru.skypro.homework.dto.*;

public interface AdService {
    Ads getAllAds();

    Ad createAd(CreateOrUpdateAd ad);

    ExtendedAd getAdById(Integer id);

    Ad updateAd(Integer id, CreateOrUpdateAd ad);

    void deleteAd(Integer id);

    Ads getUserAds();

    void updateAdImage(Integer id, IdImageBody imageBody);
}
