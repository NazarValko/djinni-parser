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
     * @param resourceId  id of parsed resource
     */
    public void write(List<String> parsedLinks, String resourceId) throws IOException {
        Path path = getFilePath(resourceId);
        Files.write(path, parsedLinks, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    private Path getFilePath(String resourceId) throws IOException {
        String filePath = "src/main/resources/parsedLinks/" + resourceId + ".txt";
        Path path = Paths.get(filePath);

        createDirectoryIfNotExists(path);

        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        return Paths.get(filePath);
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
     * Read data from file. If there is no data returns empty line
     *
     * @param resourceId  id of parsed resource
     * @return contents of file in list of strings format
     */
    public List<String> read(String resourceId) {
        Path filePath = Paths.get("src/main/resources/parsedLinks/" + resourceId + ".txt");

        try {
            return Files.readAllLines(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Error during read file");
            return List.of();
        }
    }

}
