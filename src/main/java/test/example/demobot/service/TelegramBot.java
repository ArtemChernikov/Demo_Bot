package test.example.demobot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import test.example.demobot.config.ConfigBot;

/**
 * @author Artem Chernikov
 * @version 1.0
 * @since 17.11.2022
 */
@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final ConfigBot config;

    public TelegramBot(ConfigBot config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

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

    private void startCommandExecute(long chatId, String username) {
        String message = "Привет, " + username + "! Как Ваши дела?";
        sendMessage(chatId, message);
    }

    private void sendMessage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {

        }
    }
}
