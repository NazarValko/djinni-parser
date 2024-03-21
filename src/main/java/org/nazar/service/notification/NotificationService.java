package org.nazar.service.notification;

import org.nazar.service.notification.strategy.NotificationStrategy;

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
}
