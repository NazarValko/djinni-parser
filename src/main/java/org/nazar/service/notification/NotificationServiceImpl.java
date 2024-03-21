package org.nazar.service.notification;

import org.nazar.service.notification.strategy.NotificationStrategy;

/**
 * Implementation of NotificationService to send notifications
 */
public class NotificationServiceImpl implements NotificationService {

    /**
     * Sends a notifications using the given strategy and its method send()
     *
     * @param strategy the strategy that will be used for sending notifications
     */
    public void send(NotificationStrategy strategy) {
        strategy.send();
    }
}
