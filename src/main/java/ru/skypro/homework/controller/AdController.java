package ru.skypro.homework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.models.ads.Ad;
import ru.skypro.homework.models.ads.CreateOrUpdateAd;
import ru.skypro.homework.models.ads.ExtendedAd;
import ru.skypro.homework.repository.AdRepository;

import java.util.List;

@RestController
@RequestMapping("/ads")
public class AdController {

    @Autowired
    private AdRepository adRepository;

    @GetMapping
    public ResponseEntity<List<Ad>> getAllAds() {

        List<Ad> ads = List.of();
        return ResponseEntity.ok(ads);
    }

    @PostMapping
    public ResponseEntity<Ad> addAd(@RequestPart("properties") CreateOrUpdateAd properties,
                                    @RequestPart("image")MultipartFile image) {

        if (image == null || image.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Ad newAd = new Ad(1, "ссылка", 123, 10_000, "ляляля");

        return ResponseEntity.status(HttpStatus.CREATED).body(newAd);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAd> getAd(@PathVariable("id") Integer id) {

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAd(@PathVariable("id") Integer id) {

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Ad> updateAd(@PathVariable("id") Integer id,
                                       @RequestBody Ad ad) {

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<List<Ad>> getAdsMe() {

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity<Void> updateImage(@PathVariable("id") Integer id,
                                            @RequestParam("image") MultipartFile image) {
        return ResponseEntity.ok().build();
    }

}
