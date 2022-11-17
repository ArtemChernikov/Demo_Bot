package test.example.demobot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import test.example.demobot.service.TelegramBot;

/**
 * @author Artem Chernikov
 * @version 1.0
 * @since 17.11.2022
 */
@Component
public class BotInitializer {
    @Autowired
    private TelegramBot bot;

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
     try {
         telegramBotsApi.registerBot(bot);
     } catch (TelegramApiException e) {

     }
    }
}
