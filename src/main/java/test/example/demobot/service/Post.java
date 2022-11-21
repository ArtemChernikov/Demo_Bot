package test.example.demobot.service;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author Artem Chernikov
 * @version 1.0
 * @since 21.11.2022
 */
@Data
@Component
public class Post {
    private String name;
    private String category;
    private String shortDescription;
    private String link;
}
