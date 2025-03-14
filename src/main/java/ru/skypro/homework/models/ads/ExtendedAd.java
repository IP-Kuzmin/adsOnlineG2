package ru.skypro.homework.models.ads;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Objects;

@Getter
public class ExtendedAd {

    @Schema(description = "ID объявления", example = "1")
    private Integer pk;

    @Schema(description = "Имя автора объявления")
    private String authorFirstName;

    @Schema(description = "Фамилия автора объявления")
    private String authorLastName;

    @Schema(description = "Описание объявления")
    private String description;

    @Schema(description = "Email автора объявления", example = "oleg.olegov@example.com")
    private String email;

    @Schema(description = "Ссылка на картинку объявления", example = "http://example.com/image.jpg")
    private String image;

    @Schema(description = "Телефон автора объявления", example = "+7 (999) 123-45-67")
    private String phone;

    @Schema(description = "Цена объявления")
    private Integer price;

    @Schema(description = "Заголовок объявления")
    private String title;

    public ExtendedAd(Integer pk, String authorFirstName, String authorLastName, String description, String email, String image, String phone, Integer price, String title) {
        this.pk = pk;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.description = description;
        this.email = email;
        this.image = image;
        this.phone = phone;
        this.price = price;
        this.title = title;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ExtendedAd that = (ExtendedAd) object;
        return Objects.equals(pk, that.pk) && Objects.equals(authorFirstName, that.authorFirstName) && Objects.equals(authorLastName, that.authorLastName) && Objects.equals(description, that.description) && Objects.equals(email, that.email) && Objects.equals(image, that.image) && Objects.equals(phone, that.phone) && Objects.equals(price, that.price) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, authorFirstName, authorLastName, description, email, image, phone, price, title);
    }
}
