package org.nazar.service;

/**
 * Implementation of NotificationService to send notifications
 */
public class NotificationServiceImpl implements NotificationService {

    /**
     * Sends a notifications using the given strategy and its method send()
     *
     * @param strategy the strategy that will be used for sending notifications
     * @param password password from program argument
     */
    public void send(NotificationStrategy strategy, String password) {
        strategy.send(password);
    }
}
