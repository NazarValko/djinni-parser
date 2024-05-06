package org.nazar.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.nazar.service.dao.VacancyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class ParserServiceImplTest {

    @Autowired
    private ParserService parserService;

    @MockBean
    private VacancyDao vacancyDaoImpl;

    /**
     * Tests whether new data will be added and returned correctly
     */
    @Test
    void getNewVacanciesTest_IfSucceed_ThenNewDataShouldBeAdded() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<String> existingData = List.of("Data1", "Data2");
        List<String> parsedData = List.of("Data1", "Data3", "Data6");

        when(vacancyDaoImpl.read("testData")).thenReturn(existingData);
        doNothing().when(vacancyDaoImpl).write(anyList(), eq("testData"));

        Method method = ParserServiceImpl.class.getDeclaredMethod("getNewVacancies", List.class, String.class);
        method.setAccessible(true);
        List<String> newData = (List<String>) method.invoke(parserService, parsedData, "testData");

        List<String> expectedNewData = List.of("Data3", "Data6");

        assertEquals(expectedNewData, newData);
    }

    /**
     * Tests that no new data is added or returned when no new items are found
     */
    @Test
    void getNewVacanciesTest_IfFail_ThenNoNewDataShouldBeAdded() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<String> existingData = List.of("Data1", "Data2");
        List<String> parsedData = List.of("Data1", "Data2");

        when(vacancyDaoImpl.read("testData")).thenReturn(existingData);
        doNothing().when(vacancyDaoImpl).write(anyList(), eq("testData"));

        Method method = ParserServiceImpl.class.getDeclaredMethod("getNewVacancies", List.class, String.class);
        method.setAccessible(true);
        List<String> newData = (List<String>) method.invoke(parserService, parsedData, "testData");

        assertTrue(newData.isEmpty());
    }
}
