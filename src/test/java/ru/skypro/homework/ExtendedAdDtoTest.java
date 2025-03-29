package ru.skypro.homework;

import org.junit.jupiter.api.Test;
import ru.skypro.homework.dto.ExtendedAd;

import static org.junit.jupiter.api.Assertions.*;

class ExtendedAdDtoTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        ExtendedAd ad = new ExtendedAd(
                1,
                "Иван",
                "Иванов",
                "Продам велосипед",
                "ivan@example.com",
                "/images/ad.png",
                "+7 (777) 123-45-67",
                15000,
                "Велосипед"
        );

        assertEquals(1, ad.getPk());
        assertEquals("Иван", ad.getAuthorFirstName());
        assertEquals("Иванов", ad.getAuthorLastName());
        assertEquals("Продам велосипед", ad.getDescription());
        assertEquals("ivan@example.com", ad.getEmail());
        assertEquals("/images/ad.png", ad.getImage());
        assertEquals("+7 (777) 123-45-67", ad.getPhone());
        assertEquals(15000, ad.getPrice());
        assertEquals("Велосипед", ad.getTitle());
    }

    @Test
    void testSettersAndToString() {
        ExtendedAd ad = new ExtendedAd();
        ad.setPk(2);
        ad.setAuthorFirstName("Анна");
        ad.setAuthorLastName("Петрова");
        ad.setDescription("Сдаю квартиру");
        ad.setEmail("anna@example.com");
        ad.setImage("flat.jpg");
        ad.setPhone("+7 701 111-22-33");
        ad.setPrice(85000);
        ad.setTitle("Квартира");

        assertEquals(2, ad.getPk());
        assertEquals("Анна", ad.getAuthorFirstName());
        assertEquals("Петрова", ad.getAuthorLastName());
        assertEquals("Сдаю квартиру", ad.getDescription());
        assertEquals("anna@example.com", ad.getEmail());
        assertEquals("flat.jpg", ad.getImage());
        assertEquals("+7 701 111-22-33", ad.getPhone());
        assertEquals(85000, ad.getPrice());
        assertEquals("Квартира", ad.getTitle());

        assertTrue(ad.toString().contains("ExtendedAd"));
    }

    @Test
    void testEqualsAndHashCode() {
        ExtendedAd ad1 = new ExtendedAd(1, "Иван", "Иванов", "Описание", "ivan@mail.ru", "img.jpg", "123", 1000, "Заголовок");
        ExtendedAd ad2 = new ExtendedAd(1, "Иван", "Иванов", "Описание", "ivan@mail.ru", "img.jpg", "123", 1000, "Заголовок");

        assertEquals(ad1, ad2);
        assertEquals(ad1.hashCode(), ad2.hashCode());
    }
}
