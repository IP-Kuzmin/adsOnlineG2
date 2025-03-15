package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdService;

@Service
public class AdServiceImpl implements AdService {
    @Override
    public Ads getAllAds() {
        return null;
    }

    @Override
    public Ad createAd(CreateOrUpdateAd ad) {
        return null;
    }

    @Override
    public ExtendedAd getAdById(Integer id) {
        return null;
    }

    @Override
    public Ad updateAd(Integer id, CreateOrUpdateAd ad) {
        return null;
    }

    @Override
    public void deleteAd(Integer id) {

    }

    @Override
    public Ads getUserAds() {
        return null;
    }

    @Override
    public void updateAdImage(Integer id, IdImageBody imageBody) {

    }
}
