package test.example.demobot.models;

import lombok.Data;

import java.util.Date;

/**
 * Класс описывает модель новости (поста) с сайта
 *
 * @author Artem Chernikov
 * @version 1.0
 * @since 21.11.2022
 */
@Data
public class Post {
    private String author;
    private String title;
    private String category;
    private String description;
    private String link;
    private Date publicationDate;

    public Post(String author, String title, String category, String description, String link, Date publicationDate) {
        this.author = author;
        this.title = title;
        this.category = category;
        this.description = description;
        this.link = link;
        this.publicationDate = publicationDate;
    }
}
