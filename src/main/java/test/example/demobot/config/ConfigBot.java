package test.example.demobot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Класс описывает модель конфигурации для Telegram-бота
 *
 * @author Artem Chernikov
 * @version 1.0
 * @since 17.11.2022
 */
@Configuration
@Data
@PropertySource("application.properties")
public class ConfigBot {
    /**
     * Поле имя Telegram-бота
     */
    @Value("${bot.name}")
    private String botName;
    /**
     * Поле токен Telegram-бота
     */
    @Value("${bot.token}")
    private String botToken;
}
