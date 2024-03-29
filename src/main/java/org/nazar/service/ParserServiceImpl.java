package org.nazar.service;

import org.nazar.service.dao.VacancyDao;
import org.nazar.service.notification.NotificationService;
import org.nazar.service.notification.NotificationServiceImpl;
import org.nazar.service.notification.strategy.EmailStrategy;
import org.nazar.service.properties.ApplicationProperties;
import org.nazar.service.dao.VacancyFileDao;

import java.awt.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ParserService executes scheduled parsing.
 */
public class ParserServiceImpl implements ParserService {
    private final NotificationService notificationService = new NotificationServiceImpl();
    private final VacancyDao vacancyDaoImpl = new VacancyFileDao();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * Starts parsing process
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
            ParserStrategy parserStrategy = entry.getKey();
            String url = entry.getValue();

            List<String> newVacancies = getNewVacancies(parse(parserStrategy, url), parserStrategy.getResourceId());
            notificationService.send(new EmailStrategy((String) ApplicationProperties.INSTANCE.getApplicationProperties().get("senderEmail"),
                    (String) ApplicationProperties.INSTANCE.getApplicationProperties().get("receiverEmail"), newVacancies.toString()));
        }
    }



    /**
     * Checks whether data exists in file. If not - add, then return unique data
     *
     * @param parsedData data from parser
     * @param resourceId id or parsed resourced
     * @return unique list of data
     */
    private List<String> getNewVacancies(List<String> parsedData, String resourceId) {
        List<String> dataFromFile = vacancyDaoImpl.read(resourceId);
        List<String> newData = filterData(parsedData, dataFromFile);

        if (!newData.isEmpty()) {
            try {
                vacancyDaoImpl.write(newData, resourceId);
            } catch (IOException e) {
                System.out.println(e);
                return List.of();
            }
        }
        return newData;
    }

    /**
     * Filters data from parser. Returns unique data (which is not in file)
     *
     * @param parsedData data from parser
     * @param dataFromFile read data from file
     * @return unique data represented in list of strings
     */
    private List<String> filterData(List<String> parsedData, List<String> dataFromFile) {
        Set<String> linesFromFileSet = new HashSet<>(dataFromFile);

        return parsedData.stream()
                .filter(line -> !linesFromFileSet.contains(line))
                .toList();
    }

}
