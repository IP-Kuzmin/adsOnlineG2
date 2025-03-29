package ru.skypro.homework;

import org.junit.jupiter.api.Test;
import ru.skypro.homework.dto.Ad;

import static org.junit.jupiter.api.Assertions.*;

class AdDtoTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        Ad ad = new Ad(1, 2, "http://image.url", 1000, "Test Title");

        assertEquals(1, ad.getPk());
        assertEquals(2, ad.getAuthor());
        assertEquals("http://image.url", ad.getImage());
        assertEquals(1000, ad.getPrice());
        assertEquals("Test Title", ad.getTitle());
    }

    @Test
    void testSettersAndToString() {
        Ad ad = new Ad();
        ad.setPk(10);
        ad.setAuthor(20);
        ad.setImage("img.png");
        ad.setPrice(500);
        ad.setTitle("Заголовок");

        assertEquals(10, ad.getPk());
        assertEquals(20, ad.getAuthor());
        assertEquals("img.png", ad.getImage());
        assertEquals(500, ad.getPrice());
        assertEquals("Заголовок", ad.getTitle());

        String expected = "Ad(pk=10, author=20, image=img.png, price=500, title=Заголовок)";
        assertTrue(ad.toString().contains("Ad"));
    }

    @Test
    void testEqualsAndHashCode() {
        Ad ad1 = new Ad(1, 2, "img.jpg", 100, "Title");
        Ad ad2 = new Ad(1, 2, "img.jpg", 100, "Title");

        assertEquals(ad1, ad2);
        assertEquals(ad1.hashCode(), ad2.hashCode());
    }
}
