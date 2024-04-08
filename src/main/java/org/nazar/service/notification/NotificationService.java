package org.nazar.service.notification;

import org.nazar.service.notification.strategy.NotificationStrategy;
import org.telegram.abilitybots.api.bot.AbilityBot;

/**
 * Defines the contract for notification services.
 */
public interface NotificationService {
    /**
     * Sends a notification using the specified strategy
     *
     * @param strategy the specified strategy for notification
     */
    void send(NotificationStrategy strategy);

    /**
     * Makes a sound after the links have been sent to the email
     */
    void makeSound();

    /**
     * Get instance of concrete ability bot
     * @return concrete bot object
     */
    AbilityBot getBot();
}
