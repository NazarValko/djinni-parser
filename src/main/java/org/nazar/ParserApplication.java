package org.nazar;

import jakarta.annotation.PostConstruct;
import org.nazar.service.ParserService;
import org.nazar.service.notification.bot.VacancyBot;
import org.nazar.service.properties.ApplicationProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@EnableAsync
@SpringBootApplication
public class ParserApplication implements CommandLineRunner {

    /**
     * Configure telegram bot. Set headless for java.awt to work in spring environment.
     */
    @PostConstruct
    public void setup() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(applicationContext.getBean("vacancyBot", VacancyBot.class));
        System.setProperty("java.awt.headless", "false");
    }
    private final ApplicationContext applicationContext;

    private final ParserService parserService;

    public ParserApplication(ParserService parserService, ApplicationContext applicationContext) {
        this.parserService = parserService;
        this.applicationContext = applicationContext;
    }

    public static void main(String[] args) {
        args = args == null ? new String[]{} : args;
        if (args.length == 0) {
            ApplicationProperties.INSTANCE.setPassword(null);
        } else {
            ApplicationProperties.INSTANCE.setPassword(args[0]);
        }
        SpringApplication.run(ParserApplication.class, args);
    }

    @Override
    public void run(String... args) {
        parserService.start();
    }
}
