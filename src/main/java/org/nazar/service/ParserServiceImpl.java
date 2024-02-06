package org.nazar.service;

import java.awt.*;
import java.io.IOException;
import java.util.List;
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
     */
    public void start() {
        Runnable scanner = () -> {
            try {
                process();
                new Robot().mouseMove(1, 1);
            } catch (IOException | AWTException e) {
                e.printStackTrace();
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
    public List<String> parse(ParserStrategy parserStrategy) throws IOException {
        return parserStrategy.parse();
    }

    /**
     * Processes the parsing task and sends notifications. Executes every 1 minute
     *
     * @throws IOException if an I/O error occurs during processing
     */
    private void process() throws IOException {
        notificationService.send(new EmailStrategy("nazar.valko09@gmail.com", "nazarvlk793@gmail.com",
                parse(new DouParserStrategy()).toString()));
        notificationService.send(new EmailStrategy("nazar.valko09@gmail.com", "nazarvlk793@gmail.com",
                parse(new DouParserStrategy()).toString()));
    }

}
