package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.models.AdModel;
import ru.skypro.homework.models.UserModel;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final AdMapper adMapper;
    private final UserRepository userRepository;

    public AdServiceImpl(AdRepository adRepository,
                         AdMapper adMapper,
                         UserRepository userRepository
                         ) {
        this.adRepository = adRepository;
        this.adMapper = adMapper;
        this.userRepository = userRepository;
    }

    @Override
    public Ads getAdsCount() {
        List<Ad> ads = adRepository.findAll().stream()
                .map(adMapper::toDto)
                .collect(Collectors.toList());
        return new Ads(ads.size(), ads);
    }

    @Override
    public Ad createAd(CreateOrUpdateAd dto, MultipartFile image) {
        AdModel model = adMapper.fromCreateDto(dto);
        model.setImage("/images/" + image.getOriginalFilename());

        // Временно заглушка: автор с ID = 1
        UserModel author = userRepository.findById(1L).orElseThrow();
        model.setAuthor(author);

        return adMapper.toDto(adRepository.save(model));
    }

    @Override
    public ExtendedAd getAdById(Integer id) {
        AdModel model = adRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return adMapper.toExtendedDto(model);
    }

    @Override
    public Ad updateAd(Integer id, CreateOrUpdateAd dto) {
        AdModel model = adRepository.findById(id).orElseThrow();
        adMapper.updateModel(model, dto);
        return adMapper.toDto(adRepository.save(model));
    }

    @Override
    public void deleteAd(Integer id) {
        adRepository.deleteById(id);
    }

    @Override
    public Ads getUserAdsCount() {
        UserModel author = userRepository.findById(1L).orElseThrow(); // заглушка
        List<Ad> myAds = adRepository.findAll().stream()
                .filter(a -> a.getAuthor().getId().equals(author.getId()))
                .map(adMapper::toDto)
                .collect(Collectors.toList());
        return new Ads(myAds.size(), myAds);
    }

    @Override
    public void updateAdImage(Integer id, MultipartFile image) {
        AdModel model = adRepository.findById(id).orElseThrow();
        model.setImage("/images/" + image.getOriginalFilename());
        adRepository.save(model);
    }
}
