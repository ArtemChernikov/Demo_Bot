package test.example.demobot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Класс служит точкой запуска Telegram-бота
 *
 * @author Artem Chernikov
 * @version 1.0
 * @since 17.11.2022
 */

@SpringBootApplication
public class DemoBotApplication {
    /**
     * Сама точка запуска
     *
     * @param args - входные параметры запуска
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoBotApplication.class, args);
    }

}
