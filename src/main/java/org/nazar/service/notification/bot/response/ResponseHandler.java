package org.nazar.service.notification.bot.response;

import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * Sends responses to user
 */
public class ResponseHandler {
    private final SilentSender sender;

    /**
     * Initializes object of SilentSender
     * @param sender object that sends message
     */
    public ResponseHandler(SilentSender sender) {
        this.sender = sender;
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
        sender.execute(message);
    }
}
