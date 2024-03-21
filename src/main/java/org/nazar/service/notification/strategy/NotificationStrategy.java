package org.nazar.service.notification.strategy;

/**
 * Defines a strategy for sending notifications
 */
public interface NotificationStrategy {
    /**
     * Sends a notification according to the specific implementation of the strategy.
     */
    void send();
}
