package ru.skypro.homework.components;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.skypro.homework.models.AdModel;
import ru.skypro.homework.repository.AdRepository;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class AdImageValidator {

    @Value("/${app.upload.dir}/images/")
    private String imageDir;

    @Value("${app.photo}")
    private String defaultImage;

    private final AdRepository adRepository;

    @PostConstruct
    public void checkImages() {
        String flag = System.getProperty("check.ad.images", "false");
        if (!Boolean.parseBoolean(flag)) {
            log.info("⏭️ Проверка изображений отключена (флаг check.ad.images = false)");
            return;
        }

        log.info("🔍 Запущена проверка объявлений на наличие изображений...");
        List<AdModel> ads = adRepository.findAll();
        int corrected = 0;

        for (AdModel ad : ads) {
            String rawPath = ad.getImage();
            if (rawPath.startsWith("/")) {
                rawPath = rawPath.substring(1);
            }

            Path fullPath = Path.of(rawPath.replaceAll("[:*?\"<>|]", "")
                    .replaceAll("/{2,}", "/"));;

            if (!Files.exists(fullPath)) {
                log.warn("🖼️ Недоступно: {} → замена на {}", fullPath, imageDir + defaultImage);
                ad.setImage(imageDir + defaultImage);
                corrected++;
            }
        }

        if (corrected > 0) {
            adRepository.saveAll(ads);
            log.info("✅ Завершено: {} объявлений обновлено", corrected);
        } else {
            log.info("✅ Все изображения валидны.");
        }
    }
}