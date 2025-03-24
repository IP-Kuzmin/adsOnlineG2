package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdService;

import javax.validation.Valid;

@RestController
@RequestMapping("/ads")
@Tag(name = "Объявления", description = "Управление объявлениями: добавление, редактирование, получение и удаление объявлений")
@CrossOrigin(value = "http://localhost:3000")
public class AdController {

    private final AdService adService;

    public AdController(AdService adService) {
        this.adService = adService;
    }

    @Operation(summary = "Получение всех объявлений", description = "Возвращает список всех доступных объявлений.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список объявлений успешно получен")
    })
    @GetMapping
    public ResponseEntity<Ads> getAllAds() {
        return ResponseEntity.ok(adService.getAdsCount());
    }

    @Operation(summary = "Добавление нового объявления", description = "Позволяет пользователю добавить новое объявление с указанием заголовка, описания и цены.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Объявление успешно добавлено"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для создания объявления"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован")
    })
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Ad> addAd(
            @RequestPart("properties") @Valid CreateOrUpdateAd ad,
            @RequestPart("image") MultipartFile image
    ) {
        return ResponseEntity.status(201).body(adService.createAd(ad, image));
    }

    @Operation(summary = "Получение объявления по ID", description = "Возвращает подробную информацию о конкретном объявлении по его ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация об объявлении успешно получена"),
            @ApiResponse(responseCode = "404", description = "Объявление с таким ID не найдено")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAd> getAd(@PathVariable Integer id) {
        return ResponseEntity.ok(adService.getAdById(id));
    }

    @Operation(summary = "Обновление объявления", description = "Позволяет обновить заголовок, описание и цену существующего объявления.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Объявление успешно обновлено"),
            @ApiResponse(responseCode = "404", description = "Объявление с таким ID не найдено"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Ad> updateAd(@PathVariable Integer id, @RequestBody @Valid CreateOrUpdateAd ad) {
        return ResponseEntity.ok(adService.updateAd(id, ad));
    }

    @Operation(summary = "Удаление объявления", description = "Позволяет удалить существующее объявление по его ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Объявление успешно удалено"),
            @ApiResponse(responseCode = "404", description = "Объявление с таким ID не найдено"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAd(@PathVariable Integer id) {
        adService.deleteAd(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    @Operation(summary = "Получение объявлений пользователя", description = "Возвращает список объявлений, созданных текущим авторизованным пользователем.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список объявлений пользователя успешно получен"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован")
    })
    public ResponseEntity<Ads> getMyAds() {
        return ResponseEntity.ok(adService.getUserAdsCount());
    }

    @PatchMapping(value = "/{id}/image", consumes = {"multipart/form-data"})
    @Operation(summary = "Обновление изображения объявления", description = "Позволяет обновить изображение, связанное с конкретным объявлением.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Изображение объявления успешно обновлено"),
            @ApiResponse(responseCode = "404", description = "Объявление с таким ID не найдено"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован")
    })
    public ResponseEntity<Void> updateAdImage(@PathVariable Integer id,
                                              @RequestPart("image") MultipartFile image) {
        adService.updateAdImage(id, image);
        return ResponseEntity.ok().build();
    }
}
