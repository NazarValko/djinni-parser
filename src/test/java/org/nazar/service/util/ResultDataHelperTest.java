package org.nazar.service.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResultDataHelperTest {
    private static final String filePath = "src/main/resources/data/testData.txt";
    private static final Path path = Paths.get(filePath);

    @BeforeEach
    public void setUp() throws IOException {

        List<String> initialContent = List.of("Data1", "Data2");
        Files.write(path, initialContent);
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(path);
    }

    @Test
    void writeDataTest_IfSucceed_ThenDataShouldBeWritten() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        List<String> content = List.of("Data3", "Data4");
        Method method = ResultDataHelper.class.getDeclaredMethod("writeData", List.class, String.class);
        method.setAccessible(true);
        method.invoke(null, content, filePath);
        assertTrue(Files.exists(path), "File should exist after writeData.");

        List<String> expected = List.of("Data1", "Data2", "Data3", "Data4");
        List<String> actual = Files.readAllLines(path);
        assertEquals(expected, actual);
    }

    @Test
    void readDataTest_IfSucceed_ThenDataShouldBeReturned() {
        List<String> expected = List.of("Data1", "Data2");
        List<String> actual = ResultDataHelper.readData(filePath);
        assertEquals(expected, actual);
    }

    @Test
    void readDataTest_IfFail_ThenEmptyListShouldBeReturned() {
        String nonExistentFilePath = "src/main/resources/data/nonExistentFile.txt";
        List<String> actual = ResultDataHelper.readData(nonExistentFilePath);
        assertTrue(actual.isEmpty());
    }

    @Test
    void checkIfExistsInFileIfNoAddTest_IfSucceed_ThenNewDataShouldBeAdded() throws IOException {
        List<String> parsedData = List.of("Data1", "Data3", "Data6");
        List<String> newData = ResultDataHelper.checkIfExistsInFileIfNoAdd(parsedData, "testData.txt");
        List<String> expectedNewData = List.of("Data3", "Data6");
        assertEquals(expectedNewData, newData);

        List<String> expectedFileContent = List.of("Data1", "Data2", "Data3", "Data6");
        List<String> actualFileContent = Files.readAllLines(path);
        assertEquals(expectedFileContent, actualFileContent);
    }

    @Test
    void checkIfExistsInFileIfNoAddTest_IfFail_ThenDataIsNotAdded() throws IOException {
        List<String> parsedData = List.of("Data1", "Data2");
        List<String> newData = ResultDataHelper.checkIfExistsInFileIfNoAdd(parsedData, "testData.txt");

        assertTrue(newData.isEmpty());

        List<String> expectedFileContent = List.of("Data1", "Data2");
        List<String> actualFileContent = Files.readAllLines(path);
        assertEquals(expectedFileContent, actualFileContent);
    }

}
