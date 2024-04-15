package org.nazar.service.notification.bot;

import org.nazar.service.properties.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Manages bot. Receives messages from user and responses
 */
@Component
public class VacancyBot extends AbilityBot {

    private static final Logger logger = LoggerFactory.getLogger(VacancyBot.class);

    /**
     * Call protected constructor of class AbilityBot
     * @param env object that manages env properties
     */
    public VacancyBot(Environment env) {
        super(env.getProperty("BOT_TOKEN"), "RelevantVacanciesSenderBot");
    }

    /**
     * Get creator id
     * @return id
     */
    @Override
    public long creatorId() {
        return 1L;
    }

    /**
     * Invokes when user sends anything
     * @param update update the update received from the Telegram API,
     * containing message details
     */
    @Override
    public void onUpdateReceived(Update update) {
        logger.debug("Received update: {}", update);
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String userName = update.getMessage().getFrom().getUserName();

            logger.info("Message from chat ID: {}", chatId);
            if (!isUserRegistered()) {
                String welcomeMessage = "Thank you for registration in Vacancy notification bot!";
                sendInitialMessage(chatId, welcomeMessage);
                logger.info("User registered: {} (Chat ID: {})", userName, chatId);
                registerUser(chatId);
            } else {
                registerUser(chatId);
            }
        }
    }

    /**
     * Sends parsed links to user
     * @param message links from parser
     */
    public void sendMessage(String message) {
        sendParsedLinks(ApplicationProperties.INSTANCE.getChatId(), message);
    }

    /**
     * Add chatId to properties
     * @param chatId id of chat
     */
    public void registerUser(Long chatId) {
        ApplicationProperties.INSTANCE.setChatId(chatId);
    }

    /**
     * Check if chatId exists when yes then user is registered
     * @return boolean
     */
    private boolean isUserRegistered() {
        return ApplicationProperties.INSTANCE.getChatId() != null;
    }

    /**
     * Sends links to user
     * @param chatId id of chat
     * @param parsedLinks parsed links
     */
    public void sendParsedLinks(Long chatId, String parsedLinks) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(parsedLinks);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            logger.error("Error trying to send message");
        }
    }

    /**
     * Sends initial registration message to user
     * @param chatId id of chat
     * @param message welcome message
     */
    private void sendInitialMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            logger.error("Error trying to send message");
        }
    }
}
