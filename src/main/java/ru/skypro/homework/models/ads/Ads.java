package ru.skypro.homework.models.ads;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class Ads {

    @Schema(description = "общее количество объявлений", example = "100")
    private Integer count; // общее количество объявлений

    @Schema(description = "список объявлений")
    private List<Ad> results; // список объявлений

    public Ads(Integer count, List<Ad> results) {
        this.count = count;
        this.results = results;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Ads ads = (Ads) object;
        return Objects.equals(count, ads.count) && Objects.equals(results, ads.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, results);
    }
}
