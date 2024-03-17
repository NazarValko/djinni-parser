package org.nazar.service.dao;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Utility class for managing response data from parser
 */
public class VacancyFileDao implements VacancyDao {

    /**
     * Writes data represented in list of strings to file in data directory
     *
     * @param parsedLinks data got from parser
     * @param filePath path to file
     */
    public void write(List<String> parsedLinks, String filePath) {
        try {
            Path path = Paths.get(filePath);
            Files.write(path, parsedLinks, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Cannot resolve path: " + e.getMessage());
        }
    }

    /**
     * Read data from file. If there is no data returns empty line
     *
     * @param pathToFile path to file
     * @return contents of file in list of strings format
     */
    public List<String> read(String pathToFile) {
        try {
            return Files.readAllLines(Paths.get(pathToFile), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Error during read file");
            return List.of();
        }
    }

}
