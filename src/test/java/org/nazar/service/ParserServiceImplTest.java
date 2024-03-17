package org.nazar.service;

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

public class ParserServiceImplTest {

    /**
     * Tests whether new data will be added to file
     *
     * @throws IOException when occurs
     */
    @Test
    void checkIfExistsInFileIfNoAddTest_IfSucceed_ThenNewDataShouldBeAdded() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Path temp = Paths.get("src/main/resources/parsedLinks/testData.txt");
        Files.createDirectories(temp.getParent());

        List<String> initialFileContent = List.of("Data1", "Data2");
        Files.write(temp, initialFileContent);

        List<String> parsedData = List.of("Data1", "Data3", "Data6");
        ParserServiceImpl parserService = new ParserServiceImpl();
        Method method = ParserServiceImpl.class.getDeclaredMethod("checkIfExistsInFileIfNoAdd", List.class, String.class);
        method.setAccessible(true);
        List<String> newData = (List<String>) method.invoke(parserService, parsedData, "testData.txt");
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
    void checkIfExistsInFileIfNoAddTest_IfFail_ThenDataIsNotAdded() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Path temp = Paths.get("src/main/resources/parsedLinks/testData.txt");
        Files.createDirectories(temp.getParent());

        List<String> initialFileContent = List.of("Data1", "Data2");
        Files.write(temp, initialFileContent);

        List<String> parsedData = List.of("Data1", "Data2");
        ParserServiceImpl parserService = new ParserServiceImpl();
        Method method = ParserServiceImpl.class.getDeclaredMethod("checkIfExistsInFileIfNoAdd", List.class, String.class);
        method.setAccessible(true);
        List<String> newData = (List<String>) method.invoke(parserService, parsedData, "testData.txt");

        assertTrue(newData.isEmpty());

        List<String> expectedFileContent = List.of("Data1", "Data2");
        List<String> actualFileContent = Files.readAllLines(temp);
        assertEquals(expectedFileContent, actualFileContent);

        Files.deleteIfExists(temp);
    }

}
