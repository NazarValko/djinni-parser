package org.nazar.service.notification.bot;

import org.nazar.service.properties.ApplicationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Manages bot. Receives messages from user and responses
 */
@Component
public class VacancyBot extends AbilityBot {
    private final ResponseHandler responseHandler;

    /**
     * Call protected constructor of class AbilityBot
     * @param env object that manages env properties
     */
    public VacancyBot(Environment env) {
        super(env.getProperty("BOT_TOKEN"), "RelevantVacanciesSenderBot");
        responseHandler = new ResponseHandler(silent);
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
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            ApplicationProperties.INSTANCE.setChatId(chatId);
        }
    }

    /**
     * Sends parsed links to user
     * @param message links from parser
     */
    public void sendMessage(String message) {
        responseHandler.sendParsedLinks(ApplicationProperties.INSTANCE.getChatId(), message);
    }

    /**
     * Sends responses to user
     */
    private record ResponseHandler(SilentSender sender) {

        /**
         * Initializes object of SilentSender
         *
         * @param sender object that sends message
         */
        private ResponseHandler {
        }

        /**
         * Sends links to user
         *
         * @param chatId id of chat
         * @param parsedLinks parsed links
         */
        public void sendParsedLinks(Long chatId, String parsedLinks) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(parsedLinks);
            sender.execute(message);
        }
    }
}
