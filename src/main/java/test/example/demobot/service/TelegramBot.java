package test.example.demobot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import test.example.demobot.config.ConfigBot;

/**
 * Класс описывает модель Telegram-бота
 *
 * @author Artem Chernikov
 * @version 1.0
 * @since 17.11.2022
 */
@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    /**
     * Конфигурационный объект {@link ConfigBot}
     */
    private final ConfigBot config;

    public TelegramBot(ConfigBot config) {
        this.config = config;
    }

    /**
     * Стандартный геттер для имени бота
     *
     * @return - возвращает имя бота
     */
    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    /**
     * Стандартный геттер для токена бота
     *
     * @return - возвращает токен бота
     */
    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    /**
     * Метод используется для взаимодействия пользователя с ботом
     * На данный момент присутствует только функция "/start"
     * Switch добавлен намеренно, для дальнейшего расширения взаимодействий
     *
     * @param update - обновление со стороны пользователя чата {@link Update}
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String userMessageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            switch (userMessageText) {
                case "/start" -> startCommandExecute(chatId, update.getMessage().getChat().getUserName());
                default -> sendMessage(chatId, "Извините, данная команда не поддерживается.");
            }
        }
    }

    /**
     * Метод используется для ответа бота пользователю на команду "/start"
     *
     * @param chatId   - id чата пользователя
     * @param username - имя пользователя
     */
    private void startCommandExecute(long chatId, String username) {
        String message = "Привет, " + username + "! Как Ваши дела?";
        sendMessage(chatId, message);
    }

    /**
     * Метод используется для отправки сообщений пользователю по chatId
     *
     * @param chatId  - id чата пользователя
     * @param message - текст сообщения
     */
    private void sendMessage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Произошла ошибка " + e.getMessage());
        }
    }
}
