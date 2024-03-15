package org.nazar.service.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nazar.service.ParserServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for ResultDataHelper class
 */
public class VacancyDaoTest {
    private static final String FILE_PATH = "src/test/resources/testData.txt";
    private static final Path PATH = Paths.get(FILE_PATH);


    /**
     * Create file for testing
     *
     * @throws IOException when occurs
     */
    @BeforeEach
    public void setUp() throws IOException {
        List<String> initialContent = List.of("Data1", "Data2");
        Files.write(PATH, initialContent);
    }

    /**
     * Clear test data
     *
     * @throws IOException when occurs
     */
    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(PATH);
    }

    /**
     * Tests whether data was written successfully
     *
     * @throws IOException when occurs
     */
    @Test
    void writeDataTest_IfSucceed_ThenDataShouldBeWritten() throws IOException {
        List<String> content = List.of("Data3", "Data4");
        VacancyDao vacancyDao = new VacancyDao();
        vacancyDao.writeData(content, FILE_PATH);
        assertTrue(Files.exists(PATH), "File should exist after writeData.");

        List<String> expected = List.of("Data1", "Data2", "Data3", "Data4");
        List<String> actual = Files.readAllLines(PATH);
        assertEquals(expected, actual);
    }

    /**
     * When data was read successfully then it should be returned
     *
     */
    @Test
    void readDataTest_IfSucceed_ThenDataShouldBeReturned() {
        List<String> expected = List.of("Data1", "Data2");
        VacancyDao vacancyDao = new VacancyDao();
        List<String> actual = vacancyDao.readData(FILE_PATH);
        assertEquals(expected, actual);
    }

    /**
     * When during read exception happened data should not be returned
     *
     */
    @Test
    void readDataTest_IfFail_ThenEmptyListShouldBeReturned() {
        String nonExistentFilePath = "src/test/resources/nonExistentFile.txt";
        VacancyDao vacancyDao = new VacancyDao();
        List<String> actual = vacancyDao.readData(nonExistentFilePath);
        assertTrue(actual.isEmpty());
    }

    /**
     * Tests whether new data will be added to file
     *
     * @throws IOException when occurs
     */
    @Test
    void checkIfExistsInFileIfNoAddTest_IfSucceed_ThenNewDataShouldBeAdded() throws IOException {
        Path temp = Paths.get("src/main/resources/parsedLinks/testData.txt");
        Files.createDirectories(temp.getParent());

        List<String> initialFileContent = List.of("Data1", "Data2");
        Files.write(temp, initialFileContent);

        List<String> parsedData = List.of("Data1", "Data3", "Data6");
        ParserServiceImpl parserService = new ParserServiceImpl();
        List<String> newData = parserService.checkIfExistsInFileIfNoAdd(parsedData, "testData.txt");
        List<String> expectedNewData = List.of("Data3", "Data6");
        assertEquals(expectedNewData, newData);

        List<String> expectedFileContent = List.of("Data1", "Data2", "Data3", "Data6");
        List<String> actualFileContent = Files.readAllLines(temp);
        assertEquals(expectedFileContent, actualFileContent);

        Files.deleteIfExists(temp);
    }

    /**
     * Tests that data should not be added when file is not exists
     *
     * @throws IOException when occurs
     */
    @Test
    void checkIfExistsInFileIfNoAddTest_IfFail_ThenDataIsNotAdded() throws IOException {
        Path temp = Paths.get("src/main/resources/parsedLinks/testData.txt");
        Files.createDirectories(temp.getParent());

        List<String> initialFileContent = List.of("Data1", "Data2");
        Files.write(temp, initialFileContent);

        List<String> parsedData = List.of("Data1", "Data2");
        ParserServiceImpl parserService = new ParserServiceImpl();
        List<String> newData = parserService.checkIfExistsInFileIfNoAdd(parsedData, "testData.txt");

        assertTrue(newData.isEmpty());

        List<String> expectedFileContent = List.of("Data1", "Data2");
        List<String> actualFileContent = Files.readAllLines(temp);
        assertEquals(expectedFileContent, actualFileContent);

        Files.deleteIfExists(temp);
    }

}
