package ru.skypro.homework.models.ads;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Size;
import java.util.Objects;

public class CreateOrUpdateAd {

    @Schema(description = "заголовок объявления")
    @Size(min = 4, max = 32, message = "Заголовок должен содержать от 4 до 32 символов")
    private String title;

    @Schema(description = "цена объявления")
    @Size(min = 0, max = 10_000_000)
    private Integer price;

    @Schema(description = "описание объявления")
    @Size(min = 8, max = 64, message = "Описание должно содержать от 8 до 64 символов")
    private String description;

    public CreateOrUpdateAd(String title, Integer price, String description) {
        this.title = title;
        this.price = price;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public Integer getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CreateOrUpdateAd that = (CreateOrUpdateAd) object;
        return Objects.equals(title, that.title) && Objects.equals(price, that.price) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, price, description);
    }
}
