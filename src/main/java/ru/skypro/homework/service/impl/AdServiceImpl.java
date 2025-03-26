package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.exceptions.*;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.models.AdModel;
import ru.skypro.homework.models.UserModel;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final AdMapper adMapper;

    @Value("${app.upload.dir}")
    private String imageDir;

    @Value("${app.photo}")
    private String imageDefault;

    @Value("${app.upload.absolute-path}")
    private boolean isUseAbsolutePath;

    @Override
    public Ads getAdsCount() {
        List<AdModel> allAds = adRepository.findAll();
        List<Ad> ads = allAds.stream().map(adMapper::toDto).toList();
        log.debug("Найдено объявлений: {}", ads.size());
        return new Ads(ads.size(), ads);
    }

    @Override
    public Ads getUserAdsCount() {
        UserModel user = getCurrentUser();
        List<AdModel> userAdModels = adRepository.findByAuthorId(user.getId());
        List<Ad> userAds = userAdModels.stream().map(adMapper::toDto).toList();
        log.debug("Объявлений пользователя {}: {}", user.getEmail(), userAds.size());
        return new Ads(userAds.size(), userAds);
    }

    @Override
    public ExtendedAd getAdById(Long id) {
        AdModel model = adRepository.findById(id)
                .orElseThrow(AdNotFoundResponseException::new);
        log.debug("Объявление по id {} найдено", id);
        return adMapper.toExtendedDto(model);
    }

    @Override
    public Ad createAd(CreateOrUpdateAd dto, MultipartFile image) {
        UserModel user = getCurrentUser();
        AdModel model = adMapper.fromCreateDto(dto);
        model.setAuthor(user);
        model.setImage(saveAdImage(image));  // универсальный метод
        AdModel saved = adRepository.save(model);
        log.info("Создано объявление id={}, автор={}", saved.getId(), user.getEmail());
        return adMapper.toDto(saved);
    }

    @Override
    public Ad updateAd(Long id, CreateOrUpdateAd dto) {
        AdModel model = adRepository.findById(id)
                .orElseThrow(AdNotFoundResponseException::new);
        adMapper.updateModel(model, dto);
        AdModel saved = adRepository.save(model);
        log.info("Объявление id={} обновлено", saved.getId());
        return adMapper.toDto(saved);
    }

    @Override
    public void updateAdImage(Long id, MultipartFile image) {
        AdModel model = adRepository.findById(id)
                .orElseThrow(AdNotFoundResponseException::new);
        model.setImage(saveAdImage(image));
        AdModel saved = adRepository.save(model);
        log.info("Изображение для объявления id={} обновлено", saved.getId());
    }

    @Override
    public void deleteAd(Long id) {
        adRepository.deleteById(id);
        log.info("Объявление id={} удалено", id);
    }

    private UserModel getCurrentUser() {
        String email = getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(CurrentUserNotFoundResponseException::new);
    }

    private String saveAdImage(MultipartFile image) {
        if (image == null || image.isEmpty() || image.getOriginalFilename() == null) {
            return imageDir + imageDefault;
        }

        String filename = image.getOriginalFilename();
        String absolutePath = "";
        if (!isUseAbsolutePath) {
            System.out.println("NOOOOO!");
            absolutePath = System.getProperty("user.dir");
        }
        Path savePath = Paths.get(absolutePath, imageDir, "/images/ads", filename);
        try {
            Files.createDirectories(savePath.getParent());
            image.transferTo(savePath.toFile());
        } catch (IOException e) {
            log.error("Ошибка при сохранении файла {}: {}", filename, e.getMessage());
            throw new ImageAdsSaveException(e.getMessage());
        }
        return "/".concat(imageDir) + "/images/ads/" + filename;
    }
}
