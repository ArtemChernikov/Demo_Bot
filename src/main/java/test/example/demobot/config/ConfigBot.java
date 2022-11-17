package test.example.demobot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Artem Chernikov
 * @version 1.0
 * @since 17.11.2022
 */
@Configuration
@Data
@PropertySource("application.properties")
public class ConfigBot {
    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;
}
