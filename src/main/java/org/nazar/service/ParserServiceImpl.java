package org.nazar.service;

import org.nazar.service.notification.NotificationService;
import org.nazar.service.notification.NotificationServiceImpl;
import org.nazar.service.notification.strategy.EmailStrategy;
import org.nazar.service.properties.ApplicationProperties;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ParserService executes scheduled parsing.
 */
public class ParserServiceImpl implements ParserService {
    private final NotificationService notificationService = new NotificationServiceImpl();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    /**
     * Starts parsing process
     *
     */
    public void start() {
        Runnable scanner = () -> {
            try {
                process();
                new Robot().mouseMove(10, 10);
            } catch (IOException | AWTException e) {
                System.out.println("Cannot get data");
            } 
        };
        scheduler.scheduleAtFixedRate(scanner, 0, 1, TimeUnit.MINUTES);
    }

    /**
     * Collects data using provided by ParserStrategy class and its method parse()
     *
     * @param parserStrategy type of web page with the strategy of parsing
     * @return a list of strings (links of jobs)
     * @throws IOException if an I/O error occurs during parsing
     */
    public List<String> parse(ParserStrategy parserStrategy, String url) throws IOException {
        return parserStrategy.getData(url);
    }

    /**
     * Processes the parsing task and sends notifications. Executes every 1 minute
     *
     * @throws IOException if an I/O error occurs during processing
     */
    private void process() throws IOException {
        Map<ParserStrategy, String> strategies = Map.of(
                new DouParserStrategy(), "https://jobs.dou.ua/first-job/",
                new DjinniParserStrategy(), "https://djinni.co/jobs/?primary_keyword=Java&exp_level=no_exp"
        );
        for (Map.Entry<ParserStrategy, String> entry : strategies.entrySet()) {
            String emailBody = parse(entry.getKey(), entry.getValue()).toString();
            notificationService.send(new EmailStrategy((String) ApplicationProperties.INSTANCE.getData().get("senderEmail"),
                    (String) ApplicationProperties.INSTANCE.getData().get("receiverEmail"), emailBody));
        }
    }

}
