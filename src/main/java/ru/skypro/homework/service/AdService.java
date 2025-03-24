package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;

public interface AdService {
    Ads getAdsCount();

    Ad createAd(CreateOrUpdateAd ad, MultipartFile image);

    ExtendedAd getAdById(Integer id);

    Ad updateAd(Integer id, CreateOrUpdateAd ad);

    void deleteAd(Integer id);

    Ads getUserAdsCount();

    void updateAdImage(Integer id, MultipartFile image);
}
