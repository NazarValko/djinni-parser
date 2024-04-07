package org.nazar.service.notification.strategy;

import org.nazar.service.notification.bot.listener.ChatIdAvailableListener;
import org.nazar.service.notification.bot.VacancyBot;
import org.nazar.service.properties.ApplicationProperties;

/**
 * Strategy for telegram notification sending
 */
public class TelegramStrategy implements NotificationStrategy, ChatIdAvailableListener {
    private final VacancyBot vacancyBot;
    private final String messageBody;

    /**
     * Initializes VacancyBot object and message from user
     * @param vacancyBot VacancyBot object
     * @param messageBody message from user
     */
    public TelegramStrategy(VacancyBot vacancyBot, String messageBody) {
        this.vacancyBot = vacancyBot;
        this.messageBody = messageBody;
        ApplicationProperties.INSTANCE.addChatIdAvailableListener(this);
    }

    /**
     * Checks whether chat id is present then send message else wait
     */
    @Override
    public void send() {
        Long chatId = ApplicationProperties.INSTANCE.getChatId();
        if (chatId != null) {
            vacancyBot.getResponseHandler().sendParsedLinks(chatId, messageBody);
        }
    }

    /**
     * When chat id is available send message
     * @param chatId id of chat with bot
     */
    @Override
    public void onChatIdAvailable(Long chatId) {
        vacancyBot.getResponseHandler().sendParsedLinks(chatId, messageBody);
    }
}
