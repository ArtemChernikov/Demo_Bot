package test.example.demobot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import test.example.demobot.config.ConfigBot;
import test.example.demobot.models.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс описывает модель Telegram-бота
 *
 * @author Artem Chernikov
 * @version 1.1
 * @since 17.11.2022
 */
@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    /**
     * Поле конфигурационный объект {@link ConfigBot}
     */
    private final ConfigBot config;

    private final LentaParser lentaParser;

    public TelegramBot(ConfigBot config, LentaParser lentaParser) {
        this.config = config;
        this.lentaParser = lentaParser;
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
     * 1. Команда "/start" - выполняется приветствие
     * 2. Команда "Новости" - загружается последняя новость с сайта "<a href="https://lenta.ru/rss"></a>"
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
                case "Новости" -> newsCommandExecute(chatId, lentaParser.parseLastPost());
                default -> sendMessage(chatId, "Извините, данная команда не поддерживается.");
            }
        }
    }

    /**
     * Метод используется для ответа бота пользователю на команду "/start"
     * Выводит приветствие
     *
     * @param chatId   - id чата пользователя
     * @param username - имя пользователя
     */
    private void startCommandExecute(long chatId, String username) {
        String message = "Привет, " + username + "! Как Ваши дела?";
        sendMessage(chatId, message);
    }

    /**
     * Метод используется для ответа бота пользователю на команду "Новости"
     * Выводит последнюю новость с сайта "<a href="https://lenta.ru/rss"></a>" в специальном формате
     *
     * @param chatId - id чата пользователя
     * @param post   - новость
     */
    private void newsCommandExecute(long chatId, Post post) {
        StringBuilder message = new StringBuilder()
                .append("Категория: ")
                .append(post.getCategory())
                .append(System.lineSeparator())
                .append(System.lineSeparator())
                .append("Название: ")
                .append(post.getTitle())
                .append(System.lineSeparator())
                .append(System.lineSeparator())
                .append("Краткое описание: ")
                .append(post.getDescription())
                .append(System.lineSeparator())
                .append("Ссылка на источник: ")
                .append(post.getLink());
        sendMessage(chatId, message.toString());
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
        /* Добавляем клавиатуру */
        addKeyboard(sendMessage);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Произошла ошибка " + e.getMessage());
        }
    }

    /**
     * Метод используется для добавления клавиатуры с кнопками к сообщению
     *
     * @param sendMessage - отправляемое сообщение от Telegram-бота
     */
    private void addKeyboard(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> buttons = new ArrayList<>();
        addButton(buttons, "Новости");
        replyKeyboardMarkup.setKeyboard(buttons);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
    }

    /**
     * Метод используется для добавления кнопки к клавиатуре пользователя
     *
     * @param buttons    - список кнопок
     * @param buttonName - название кнопки
     * @return - возвращает boolean (успешно добавилась кнопка или нет)
     */
    private boolean addButton(List<KeyboardRow> buttons, String buttonName) {
        KeyboardRow row = new KeyboardRow();
        row.add(buttonName);
        if (buttons.contains(row)) {
            return false;
        }
        buttons.add(row);
        return true;
    }
}
