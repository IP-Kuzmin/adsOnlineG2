package ru.skypro.homework;

import org.junit.jupiter.api.Test;
import ru.skypro.homework.dto.Comment;

import static org.junit.jupiter.api.Assertions.*;

class CommentDtoTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        Comment comment = new Comment(
                1,
                "/avatars/user.png",
                "Иван",
                1672502400000L,
                100,
                "Хорошее объявление!"
        );

        assertEquals(1, comment.getAuthor());
        assertEquals("/avatars/user.png", comment.getAuthorImage());
        assertEquals("Иван", comment.getAuthorFirstName());
        assertEquals(1672502400000L, comment.getCreatedAt());
        assertEquals(100, comment.getPk());
        assertEquals("Хорошее объявление!", comment.getText());
    }

    @Test
    void testSettersAndToString() {
        Comment comment = new Comment();
        comment.setAuthor(5);
        comment.setAuthorImage("img.jpg");
        comment.setAuthorFirstName("Анна");
        comment.setCreatedAt(123456789L);
        comment.setPk(55);
        comment.setText("Комментарий");

        assertEquals(5, comment.getAuthor());
        assertEquals("img.jpg", comment.getAuthorImage());
        assertEquals("Анна", comment.getAuthorFirstName());
        assertEquals(123456789L, comment.getCreatedAt());
        assertEquals(55, comment.getPk());
        assertEquals("Комментарий", comment.getText());

        assertTrue(comment.toString().contains("Comment"));
    }

    @Test
    void testEqualsAndHashCode() {
        Comment c1 = new Comment(1, "img.jpg", "Иван", 123L, 10, "Привет");
        Comment c2 = new Comment(1, "img.jpg", "Иван", 123L, 10, "Привет");

        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
    }
}
