package org.nazar.service.dao;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for ResultDataHelper class
 */
public class VacancyFileDaoTest {
    private static final Path TEST_FILE_PATH = Paths.get("src/main/resources/parsedLinks/testData.txt");


    /**
     * Create file for testing
     */
    @BeforeEach
    public void setUp() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = VacancyFileDao.class.getDeclaredMethod("createDirectoryIfNotExists", Path.class);
        method.setAccessible(true);
        method.invoke(new VacancyFileDao(), TEST_FILE_PATH);
        List<String> initialContent = List.of("Data1", "Data2");
        try {
            Files.write(TEST_FILE_PATH, initialContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Clear test data
     *
     * @throws IOException when occurs
     */
    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(TEST_FILE_PATH);
        Files.deleteIfExists(Path.of("src/main/resources/parsedLinks/nonExistentFile.txt"));
    }

    /**
     * Tests whether data was written successfully
     *
     * @throws IOException when occurs
     */
    @Test
    void writeDataTest_IfSucceed_ThenDataShouldBeWritten() throws IOException {
        List<String> content = List.of("Data3", "Data4");
        VacancyFileDao vacancyFileDao = new VacancyFileDao();
        vacancyFileDao.write(content, "testData");
        assertTrue(Files.exists(TEST_FILE_PATH), "File should exist after writeData.");

        List<String> expected = List.of("Data1", "Data2", "Data3", "Data4");
        List<String> actual = Files.readAllLines(TEST_FILE_PATH);
        assertEquals(expected, actual);
    }

    /**
     * When data was read successfully then it should be returned
     */
    @Test
    void readDataTest_IfSucceed_ThenDataShouldBeReturned() {
        List<String> expected = List.of("Data1", "Data2");
        VacancyFileDao vacancyFileDao = new VacancyFileDao();
        List<String> actual = vacancyFileDao.read("testData");
        assertEquals(expected, actual);
    }

    /**
     * When during read exception happened data should not be returned
     */
    @Test
    void readDataTest_IfFail_ThenEmptyListShouldBeReturned() {
        String nonExistentFilePath = "nonExistentFile";
        VacancyFileDao vacancyFileDao = new VacancyFileDao();
        List<String> actual = vacancyFileDao.read(nonExistentFilePath);
        assertTrue(actual.isEmpty());
    }

}
