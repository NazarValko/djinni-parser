package org.nazar.service.notification.strategy;

/**
 * Defines a strategy for sending notifications
 */
public interface NotificationStrategy {
    /**
     * Sends a notification according to the specific implementation of the strategy.
     */
    void send();

    /**
     * Makes a sound after the links have been sent to the email
     */
    void makeSound();
}
