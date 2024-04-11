package org.nazar.service.notification.strategy;

import org.nazar.service.notification.bot.VacancyBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Creates beans of notifications strategies
 */
@Configuration
public class NotificationFactory {
    private final VacancyBot vacancyBot;

    public NotificationFactory(VacancyBot vacancyBot) {
        this.vacancyBot = vacancyBot;
    }

    /**
     * Creates bean of EmailStrategy
     * @param from sender
     * @param to receiver
     * @param messageBody parsed links
     * @return EmailStrategy object
     */
    @Bean(autowireCandidate = false)
    @Scope("prototype")
    public NotificationStrategy emailStrategy(String from, String to, String messageBody) {
        return new EmailStrategy(from, to, messageBody);
    }

    /**
     * Creates bean of TelegramStrategy
     * @param message parsed links
     * @return object of TelegramStrategy
     */
    @Bean(autowireCandidate = false)
    @Scope("prototype")
    public NotificationStrategy telegramStrategy(String message) {
        return new TelegramStrategy(vacancyBot, message);
    }
}
