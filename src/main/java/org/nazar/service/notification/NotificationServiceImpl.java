package org.nazar.service.notification;

import java.awt.Toolkit;
import org.nazar.service.notification.strategy.NotificationStrategy;
import org.springframework.stereotype.Service;



/**
 * Implementation of NotificationService to send notifications
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    /**
     * Sends a notifications using the given strategy and its method send()
     *
     * @param strategy the strategy that will be used for sending notifications
     */
    public void send(NotificationStrategy strategy) {
        strategy.send();
    }

    /**
     * Make sound after links will be sent
     */
    @Override
    public void makeSound() {
        Toolkit.getDefaultToolkit().beep();
    }
}
