package org.nazar.service.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Utility class for managing response data from parser
 */
public class ResultDataHelper {

    /**
     * Writes data represented in list of strings to file in data directory
     *
     * @param data data got from parser
     * @param filePath path to file
     */
    private static void writeData(List<String> data, String filePath) {
        try {
            Files.write(Paths.get(filePath), data, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
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
    public static List<String> readData(String pathToFile) {
        try {
            return Files.readAllLines(Paths.get(pathToFile), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Error during read file");
            return List.of();
        }
    }

    /**
     * Filters data from parser. Returns unique data (which is not in file)
     *
     * @param parsedData data from parser
     * @param dataFromFile read data from file
     * @return unique data represented in list of strings
     */
    public static List<String> filterData(List<String> parsedData, List<String> dataFromFile) {
        Set<String> linesFromFileSet = new HashSet<>(dataFromFile);

        return parsedData.stream()
                .filter(line -> !linesFromFileSet.contains(line))
                .toList();
    }

    /**
     * Checks whether data exists in file. If not - add, then return unique data
     *
     * @param parsedData data from parser
     * @param filename name of file to be written
     * @return unique list of data
     */
    public static List<String> checkIfExistsInFileIfNoAdd(List<String> parsedData, String filename) {
        String filePath = "src/main/resources/data/" + filename;
        Path path = Paths.get(filePath);
        if(!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                System.out.println("Cannot create file.");
            }
        }
        List<String> dataFromFile = readData(filePath);

        List<String> newData = filterData(parsedData, dataFromFile);

        if (!newData.isEmpty()) {
            writeData(newData, filePath);
        }
        return newData;
    }

}
