package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;

public interface AdService {
    Ads getAdsCount();

    Ad createAd(CreateOrUpdateAd ad, MultipartFile image);

    ExtendedAd getAdById(Long id);

    Ad updateAd(Long id, CreateOrUpdateAd ad);

    void deleteAd(Long id);

    Ads getUserAdsCount();

    void updateAdImage(Long id, MultipartFile image);
}
