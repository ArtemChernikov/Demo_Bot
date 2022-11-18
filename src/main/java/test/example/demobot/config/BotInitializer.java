package test.example.demobot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import test.example.demobot.service.TelegramBot;

/**
 * Класс описывает модель для инициализации Telegram-бота
 *
 * @author Artem Chernikov
 * @version 1.0
 * @since 17.11.2022
 */
@Slf4j
@Component
public class BotInitializer {
    /**
     * Поле модель {@link TelegramBot} для инициализации
     */
    @Autowired
    private TelegramBot bot;

    /**
     * Метод используется для инициализации Telegram-бота
     *
     * @throws TelegramApiException - может выбросить {@link TelegramApiException}
     */
    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            log.error("Произошла ошибка " + e.getMessage());
        }
    }
}
