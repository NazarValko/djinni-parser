package org.nazar.service;

import org.nazar.service.notification.NotificationService;
import org.nazar.service.notification.NotificationServiceImpl;
import org.nazar.service.notification.strategy.EmailStrategy;
import org.nazar.service.properties.ApplicationProperties;
import org.nazar.service.dao.VacancyDao;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private final VacancyDao vacancyDao = new VacancyDao();
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
     * Get unique result of parsed data. Write new data to file.
     *
     * @param parserStrategy strategy type of parsing
     * @param url            url of page
     * @return unique result
     * @throws IOException if occurs throw new exception
     */
    public List<String> getUniqueResultAndSave(ParserStrategy parserStrategy, String url) throws IOException {
        return checkIfExistsInFileIfNoAdd(parse(parserStrategy, url), parserStrategy.getFileName());
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
            String emailBody = getUniqueResultAndSave(entry.getKey(), entry.getValue()).toString();
            notificationService.send(new EmailStrategy((String) ApplicationProperties.INSTANCE.getData().get("senderEmail"),
                    (String) ApplicationProperties.INSTANCE.getData().get("receiverEmail"), emailBody));
        }
    }

    /**
     * If given directory does not exist, creates it.
     *
     * @param path the file path
     * @throws IOException if an I/O error occurs
     */
    private void createDirectoryIfNotExists(Path path) throws IOException {
        Path directoryPath = path.getParent();
        if (directoryPath != null && !Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }
    }

    /**
     * Checks whether data exists in file. If not - add, then return unique data
     *
     * @param parsedData data from parser
     * @param filename name of file to be written
     * @return unique list of data
     */
    public List<String> checkIfExistsInFileIfNoAdd(List<String> parsedData, String filename) {
        String filePath = "src/main/resources/parsedLinks/" + filename;
        Path path = Paths.get(filePath);

        try {
            createDirectoryIfNotExists(path);
        } catch (IOException e) {
            return List.of();
        }

        if(!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                System.out.println("Cannot create file.");
                return List.of();
            }
        }

        List<String> dataFromFile = vacancyDao.readData(filePath);

        List<String> newData = filterData(parsedData, dataFromFile);

        if (!newData.isEmpty()) {
            vacancyDao.writeData(newData, filePath);
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
    public List<String> filterData(List<String> parsedData, List<String> dataFromFile) {
        Set<String> linesFromFileSet = new HashSet<>(dataFromFile);

        return parsedData.stream()
                .filter(line -> !linesFromFileSet.contains(line))
                .toList();
    }

}
