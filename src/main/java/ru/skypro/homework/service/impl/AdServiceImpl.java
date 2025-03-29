package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.exceptions.*;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.models.AdModel;
import ru.skypro.homework.models.UserModel;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.enums.ImageSaveType;

import java.util.List;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final AdMapper adMapper;
    private final ImageService imageService;

    static private final String DENIED_ERROR = "Permission Ad denied - no admin privileges in Id: ";

    @Value("${app.upload.dir}")
    private String imageDir;

    @Value("${app.upload.absolute-path}")
    private boolean isUseAbsolutePath;

    @Override
    public Ads getAdsCount() {
        List<Ad> allAds = adRepository.findAll()
                .stream().map(adMapper::toDto).toList();
        log.debug("Найдено объявлений: {}", allAds.size());
        return new Ads(allAds.size(), allAds);
    }

    @Override
    public Ads getUserAdsCount() {
        UserModel user = getCurrentUser();
        List<Ad> userAds = adRepository.findByAuthorId(user.getId())
                .stream().map(adMapper::toDto).toList();
        log.debug("Объявлений пользователя {}: {}", user.getEmail(), userAds.size());
        return new Ads(userAds.size(), userAds);
    }

    @Override
    public ExtendedAd getAdById(Long id) {
        AdModel model = adRepository.findById(id)
                .orElseThrow(AdNotFoundResponseException::new);
        log.debug("Объявление по id {} найдено", id);
        if (!imageService.imageValidator(model.getImage())) {
            throw new ImageNotFoundException("Image " + model.getImage() + " not found on Ad Id: " + id);
        }
        return adMapper.toExtendedDto(model);
    }

    @Override
    public Ad createAd(CreateOrUpdateAd dto, MultipartFile image) {
        AdModel model = createAdBody(dto);
        updateAdImage(model.getId(), image);
        return adMapper.toDto(model);
    }

    private AdModel createAdBody(CreateOrUpdateAd dto) {
        UserModel user = getCurrentUser();
        AdModel model = adMapper.fromCreateDto(dto);
        model.setAuthor(user);
        AdModel saved = adRepository.save(model);
        log.debug("Создано объявление id={}, автор={}", saved.getId(), user.getEmail());
        return saved;
    }

    @Override
    public Ad updateAd(Long id, CreateOrUpdateAd dto) {
        if(isOwner(id)) {
            AdModel model = adRepository.findById(id)
                    .orElseThrow(AdNotFoundResponseException::new);
            adMapper.updateModel(model, dto);
            AdModel saved = adRepository.save(model);
            log.debug("Объявление id={} обновлено", saved.getId());
            return adMapper.toDto(saved);
        } else {
            throw new PermissionDeniedUpdateResponseException(DENIED_ERROR + id);
        }
    }

    @Override
    public void updateAdImage(Long id, MultipartFile image) {
        if (isOwner(id)) {
            AdModel model = adRepository.findById(id)
                    .orElseThrow(AdNotFoundResponseException::new);
            model.setImage(imageService.saveImage(image, id, ImageSaveType.AD));
            AdModel saved = adRepository.save(model);
            log.debug("Изображение для объявления id={} обновлено", saved.getId());
        } else {
            throw new PermissionDeniedUpdateResponseException(DENIED_ERROR + id);
        }
    }

    @Override
    public void deleteAd(Long id) {
        if( isOwner(id) ) {
            adRepository.deleteById(id);
            log.debug("Объявление id={} удалено", id);
        } else {
            throw new PermissionDeniedDeleteResponseException(DENIED_ERROR + id);
        }
    }

    private UserModel getCurrentUser() {
        String email = getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(CurrentUserNotFoundResponseException::new);
    }

    private boolean isOwner(Long id) {
        AdModel ad = adRepository.findById(id).orElseThrow(AdNotFoundResponseException::new);
        return getCurrentUser().getRole() == Role.ADMIN || ad.getAuthor() == getCurrentUser();
    }
}


