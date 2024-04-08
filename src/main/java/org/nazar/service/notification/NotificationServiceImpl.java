package org.nazar.service.notification;

import java.awt.Toolkit;
import org.nazar.service.notification.bot.VacancyBot;
import org.nazar.service.notification.strategy.NotificationStrategy;
import org.springframework.stereotype.Service;



/**
 * Implementation of NotificationService to send notifications
 */
@Service
public class NotificationServiceImpl implements NotificationService {
    private final VacancyBot vacancyBot;

    public NotificationServiceImpl(VacancyBot vacancyBot) {
        this.vacancyBot = vacancyBot;
    }

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

    /**
     * Get concrete bot object
     * @return bot object
     */
    @Override
    public VacancyBot getBot() {
        return vacancyBot;
    }
}
