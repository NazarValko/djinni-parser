package org.nazar.service.notification.bot.listener;

/**
 * Interface to listen whether chat id is present
 */
public interface ChatIdAvailableListener {
    void onChatIdAvailable(Long chatId);
}
