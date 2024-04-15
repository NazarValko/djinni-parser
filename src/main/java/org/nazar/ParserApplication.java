package org.nazar;

import org.nazar.service.ParserServiceImpl;
import org.nazar.service.notification.bot.VacancyBot;
import org.nazar.service.properties.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@SpringBootApplication
public class ParserApplication {

    public static void main(String[] args) throws TelegramApiException {
        System.setProperty("java.awt.headless", "false");
        ApplicationProperties.INSTANCE.readArgsFromCommandLine(args);
        ConfigurableApplicationContext ctx =
                SpringApplication.run(ParserApplication.class, args);
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(ctx.getBean("vacancyBot", VacancyBot.class));
        ctx.getBean("parserServiceImpl", ParserServiceImpl.class).start();
    }
}
