package test.example.demobot.interfaces;

import test.example.demobot.models.Post;

/**
 * Используется для реализации классами-парсерами сайтов
 *
 * @author Artem Chernikov
 * @version 1.0
 * @since 17.11.2022
 */
public interface FeedParser {
    Post parseLastPost();
}
